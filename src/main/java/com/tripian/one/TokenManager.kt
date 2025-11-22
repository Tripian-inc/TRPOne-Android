package com.tripian.one

import android.text.TextUtils
import com.google.gson.Gson
import com.tripian.one.api.users.TUsers
import com.tripian.one.api.users.model.RefreshTokenResponse
import com.tripian.one.api.users.model.Token
import com.tripian.one.util.PreferencesOne
import com.tripian.one.util.TLogger
import com.tripian.one.util.exception.TokenFailException
import kotlin.math.abs

object TokenManager {
    private val users: TUsers by lazy { TUsers() }

    private val gson = Gson()

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

        val isExpired = abs(token!!.expiresIn!! - ((System.currentTimeMillis() - loginTime) / 1000)) < 40

        return if (token == null || token?.expiresIn == null) {
            true
        } else {
            TLogger.log("TokenManager.isRefreshTokenExpired isExpired: $isExpired")
            isExpired
        }
    }

    suspend fun checkToken() {
        // Token info control
        TLogger.log("TokenManager.checkToken started")

        if (isAccessTokenExpired()) {
            val res = refreshToken()

            if (res.isSuccess) {
                TLogger.log("TokenManager.checkToken refreshToken success")

                token = res.getOrNull()?.data
            } else {
                TLogger.log("TokenManager.checkToken refreshToken fail signOut called")

                TLogger.log("TokenManager.checkToken refreshToken fail")

                throw res.exceptionOrNull() ?: TokenFailException("RefreshToken fail")
            }
        }

        TLogger.log("TokenManager.checkToken ended")
    }

    suspend fun refreshToken(): Result<RefreshTokenResponse> {
        return runCatching { users.refreshToken(token?.refreshToken) }
    }

    fun clear() {
        TLogger.log("token cleared")
        token = null
    }

    fun headerToken() = token?.idToken?.let { "${token?.tokenType} ${token?.idToken}" } ?: run { null }

    fun tokenReceived(data: Token?) {
        token = data
    }
}