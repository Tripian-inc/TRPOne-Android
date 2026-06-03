package com.tripian.one.network

import android.os.Build
import com.google.gson.GsonBuilder
import com.tripian.one.TokenManager
import com.tripian.one.util.TLogger
import okhttp3.CacheControl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TNetwork {

    /**
     * Mirrors the iOS SDK's User-Agent string:
     *   TRPCoreKit (<bundleId>/<version>; Build/<build>; <os>/<osVersion>; <device>)
     * Resolved once via lazy; TConfig.appContext must be initialized before any
     * request fires, which TRPRest.Builder guarantees during SDK setup.
     */
    private val userAgentValue: String by lazy {
        val ctx = TConfig.appContext
        val pkg = ctx.packageName
        val info = try {
            ctx.packageManager.getPackageInfo(pkg, 0)
        } catch (_: Exception) {
            null
        }
        val appVersion = info?.versionName ?: "0"
        val buildNumber = when {
            info == null -> "0"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> info.longVersionCode.toString()
            else -> {
                @Suppress("DEPRECATION")
                info.versionCode.toString()
            }
        }
        val osVersion = Build.VERSION.RELEASE ?: "0"
        val deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
        "TRPCoreKit-Android ($pkg/$appVersion; Build/$buildNumber; Android/$osVersion; $deviceModel)"
    }

//    private fun provideCertificate(): CertificatePinner {
//        return CertificatePinner.Builder()
//            .build()
//    }

    fun okHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor() {
            TLogger.log("Okhttp: $it")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

//        if (TConfig.config.sslPinningEnabled) {
//            builder.certificatePinner(provideCertificate())
//        }

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequestBuilder = originalRequest.newBuilder()

            // SDK identity header on every outbound request. `header(...)` is
            // used (not `addHeader`) so we overwrite the default User-Agent
            // OkHttp would otherwise stamp on the request.
            newRequestBuilder.header("User-Agent", userAgentValue)

            // For POST requests, add lang to body instead of query parameter
            if (originalRequest.method == "POST") {
                val originalBody = originalRequest.body
                if (originalBody != null && originalBody.contentType()?.subtype == "json") {
                    try {
                        // Read original JSON body
                        val buffer = Buffer()
                        originalBody.writeTo(buffer)
                        val originalJson = buffer.readUtf8()

                        // Parse and add lang if not present
                        val jsonObject = JSONObject(originalJson)
                        if (!jsonObject.has("lang")) {
                            jsonObject.put("lang", TConfig.lang)
                        }

                        // Create new body with lang
                        val newBody = jsonObject.toString()
                            .toRequestBody("application/json;charset=UTF-8".toMediaType())

                        newRequestBuilder.method(originalRequest.method, newBody)
                    } catch (e: Exception) {
                        TLogger.log("TNetwork: Failed to add lang to body: ${e.message}")
                        // If JSON parsing fails, keep original body
                    }
                }

                // POST requests don't get lang as query parameter
                newRequestBuilder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("x-api-key", TConfig.key)
                    .cacheControl(CacheControl.FORCE_NETWORK)
            } else {
                // GET, PUT, DELETE: add lang as query parameter
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("lang", TConfig.lang)
                    .build()

                newRequestBuilder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("x-api-key", TConfig.key)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .url(url)
            }

            TokenManager.headerToken()?.let { newRequestBuilder.addHeader("Authorization", it) }

            return@addInterceptor chain.proceed(newRequestBuilder.build())
        }

        val sessionTimeout = 120L

        builder.addInterceptor(interceptor)

        builder.connectTimeout(sessionTimeout, TimeUnit.SECONDS)
        builder.readTimeout(sessionTimeout, TimeUnit.SECONDS)
        builder.writeTimeout(sessionTimeout, TimeUnit.SECONDS)

        // Only bypass hostname verification in debug mode
        // WARNING: Disabling hostname verification in production is a security risk
        if (TConfig.isDebugMode) {
            builder.hostnameVerifier { _, _ -> true }
        }

        return builder.build()
    }

    inline fun <reified T> createService(): T {
        return Retrofit.Builder()
            .baseUrl(TConfig.url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttp())
            .build().create(T::class.java)
    }
}