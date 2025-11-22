package com.tripian.one.network

import com.google.gson.GsonBuilder
import com.tripian.one.TokenManager
import com.tripian.one.util.TLogger
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TNetwork {

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

            TokenManager.headerToken()?.let { newRequestBuilder.addHeader("Authorization", it) }

            return@addInterceptor chain.proceed(newRequestBuilder.build())
        }

        val sessionTimeout = 120L

        builder.addInterceptor(interceptor)

        builder.connectTimeout(sessionTimeout, TimeUnit.SECONDS)
        builder.readTimeout(sessionTimeout, TimeUnit.SECONDS)
        builder.writeTimeout(sessionTimeout, TimeUnit.SECONDS)

        builder.hostnameVerifier { hostname, session -> true }

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