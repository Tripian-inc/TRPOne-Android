package com.tripian.one

import android.text.TextUtils
import com.google.gson.Gson
import com.tripian.one.api.users.TUsers
import com.tripian.one.api.users.model.RefreshTokenResponse
import com.tripian.one.api.users.model.Token
import com.tripian.one.util.PreferencesOne
import com.tripian.one.api.users.model.LightLoginRequest
import com.tripian.one.network.TConfig
import com.tripian.one.util.TLogger
import com.tripian.one.util.exception.TokenFailException
import kotlin.math.abs
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import com.tripian.one.util.event.TokenInvalidatedEvent

object TokenManager {
    private val users: TUsers by lazy { TUsers() }

    private val gson = Gson()

    // Ardışık hata sayacı
    private var consecutiveRefreshFailures = 0

    // Son hata zamanı (bekleme kontrolü için)
    private var lastRefreshFailureTime = 0L

    // Bekleme süresi (ms)
    private const val REFRESH_RETRY_DELAY = 5000L

    // Max ardışık hata sayısı
    private const val MAX_CONSECUTIVE_FAILURES = 2

    // Concurrent request senkronizasyonu için Mutex
    private val refreshMutex = Mutex()

    private var loginTime: Long = 0L
        get() {
            if (field == 0L) {
                val time = PreferencesOne.getLong(PreferencesOne.Keys.USER_LOGIN_TIME, 0L)

                field = time
            }

            return field
        }

    var token: Token? = null
        get() {
            if (field == null) {
                val infoJson = PreferencesOne.getString(PreferencesOne.Keys.USER_LOGIN)

                if (!TextUtils.isEmpty(infoJson)) {
                    token = gson.fromJson(infoJson, Token::class.java)
                }
            }

            return field
        }
        set(value) {
            field = value

            if (value != null) {
                // Save to device
                PreferencesOne.setString(PreferencesOne.Keys.USER_LOGIN, gson.toJson(value))
                PreferencesOne.setLong(PreferencesOne.Keys.USER_LOGIN_TIME, System.currentTimeMillis())

                // This pref data used UserBasePresenter class for header
                value.idToken?.let { PreferencesOne.setString(PreferencesOne.Keys.ACCESS_TOKEN, it) }
                value.refreshToken?.let { PreferencesOne.setString(PreferencesOne.Keys.REFRESH_TOKEN, it) }
                value.tokenType?.let { PreferencesOne.setString(PreferencesOne.Keys.TOKEN_TYPE, it) }
            } else {
                PreferencesOne.deleteKey(PreferencesOne.Keys.USER_LOGIN)
                PreferencesOne.deleteKey(PreferencesOne.Keys.USER_LOGIN_TIME)
            }
        }

    init {
        val infoJson = PreferencesOne.getString(PreferencesOne.Keys.USER_LOGIN)

        if (!infoJson.isNullOrEmpty()) {
            token = gson.fromJson(infoJson, Token::class.java)
        }
    }

    private fun isAccessTokenExpired(): Boolean {
        TLogger.log("TokenManager.isRefreshTokenExpired idToken: ${token?.idToken}")
        TLogger.log("TokenManager.isRefreshTokenExpired refreshToken: ${token?.refreshToken}")
        TLogger.log("TokenManager.isRefreshTokenExpired refreshTokenExpiresAt: ${token?.expiresIn}")

        // Check for null first before accessing
        if (token == null || token?.expiresIn == null) {
            return true
        }

        val isExpired = abs(token!!.expiresIn!! - ((System.currentTimeMillis() - loginTime) / 1000)) < 40
        TLogger.log("TokenManager.isRefreshTokenExpired isExpired: $isExpired")
        return isExpired
    }

    suspend fun checkToken() {
        // Token info control
        TLogger.log("TokenManager.checkToken started")

        if (isAccessTokenExpired()) {
            // refreshToken yoksa API çağrısı yapma
            if (token?.refreshToken.isNullOrEmpty()) {
                TLogger.log("TokenManager.checkToken - No refresh token available, skipping refresh")
                return
            }

            // Mutex ile senkronize et - concurrent request'lerde tek refresh
            refreshMutex.withLock {
                // Double-check: Mutex beklerken baska thread refresh yapmis olabilir
                if (!isAccessTokenExpired()) {
                    TLogger.log("TokenManager.checkToken - Token already refreshed by another request")
                    return
                }

                // Son hatadan sonra bekleme kontrolü
                val timeSinceLastFailure = System.currentTimeMillis() - lastRefreshFailureTime
                if (lastRefreshFailureTime > 0 && timeSinceLastFailure < REFRESH_RETRY_DELAY) {
                    val waitTime = REFRESH_RETRY_DELAY - timeSinceLastFailure
                    TLogger.log("TokenManager.checkToken - Waiting ${waitTime}ms before retry")
                    delay(waitTime)
                }

                val res = refreshToken()

                if (res.isSuccess) {
                    TLogger.log("TokenManager.checkToken refreshToken success")
                    token = res.getOrNull()?.data
                    // Başarılı - sayacı sıfırla
                    consecutiveRefreshFailures = 0
                    lastRefreshFailureTime = 0L
                } else {
                    TLogger.log("TokenManager.checkToken refreshToken fail")

                    // Fatal auth error kontrolu (401/403)
                    res.exceptionOrNull()?.let { exception ->
                        if (isFatalAuthError(exception)) {
                            TLogger.log("TokenManager.checkToken - Fatal auth error (401/403), clearing token")
                            clear()
                            throw TokenFailException("Token invalid: ${exception.message}")
                        }
                    }

                    consecutiveRefreshFailures++
                    lastRefreshFailureTime = System.currentTimeMillis()

                    // 2 ardışık hata - otomatik lightLogin
                    if (consecutiveRefreshFailures >= MAX_CONSECUTIVE_FAILURES) {
                        TLogger.log("TokenManager.checkToken - Max failures reached, performing auto lightLogin")
                        performAutoLightLogin()
                        return
                    }

                    throw res.exceptionOrNull() ?: TokenFailException("RefreshToken fail")
                }
            }
        }

        TLogger.log("TokenManager.checkToken ended")
    }

    suspend fun refreshToken(): Result<RefreshTokenResponse> {
        return runCatching { users.refreshToken(token?.refreshToken) }
    }

    /**
     * 401 veya 403 hatasi olup olmadigini kontrol eder.
     * Bu hatalar token'in gecersiz oldugunu gosterir ve retry yapilmamalidir.
     */
    private fun isFatalAuthError(exception: Throwable): Boolean {
        if (exception is HttpException) {
            return exception.code() == 401 || exception.code() == 403
        }
        return false
    }

    private suspend fun performAutoLightLogin() {
        TLogger.log("TokenManager.performAutoLightLogin started")

        try {
            val deviceId = TConfig.device.deviceId
            val uniqueId = "$deviceId@tripianguest.com"

            val request = LightLoginRequest().apply {
                this.uniqueId = uniqueId
                this.device = TConfig.device
            }

            val response = users.lightLogin(request)

            // Token'ı güncelle
            tokenReceived(response.data)

            // Sayaçları sıfırla
            consecutiveRefreshFailures = 0
            lastRefreshFailureTime = 0L

            TLogger.log("TokenManager.performAutoLightLogin success")
        } catch (e: Exception) {
            TLogger.log("TokenManager.performAutoLightLogin failed: ${e.message}")
            // LightLogin da başarısız olursa, token'ı temizle
            clear()
            throw TokenFailException("Auto lightLogin failed: ${e.message}")
        }
    }

    fun clear() {
        TLogger.log("token cleared")
        token = null
        // TRPCore'a bildir - login ekranina yonlendirmesi icin
        EventBus.getDefault().post(TokenInvalidatedEvent("Token invalidated"))
    }

    fun headerToken() = token?.idToken?.let { "${token?.tokenType} ${token?.idToken}" } ?: run { null }

    fun tokenReceived(data: Token?) {
        token = data
    }
}