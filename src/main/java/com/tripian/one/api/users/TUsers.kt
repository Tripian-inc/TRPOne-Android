package com.tripian.one.api.users

import com.tripian.one.TokenManager
import com.tripian.one.api.users.model.EmptyResponse
import com.tripian.one.api.users.model.ForgotPasswordRequest
import com.tripian.one.api.users.model.GuestLoginRequest
import com.tripian.one.api.users.model.LightLoginRequest
import com.tripian.one.api.users.model.LoginRequest
import com.tripian.one.api.users.model.LoginResponse
import com.tripian.one.api.users.model.RefreshTokenRequest
import com.tripian.one.api.users.model.RefreshTokenResponse
import com.tripian.one.api.users.model.RegisterRequest
import com.tripian.one.api.users.model.SocialLoginRequest
import com.tripian.one.api.users.model.UpdateUserRequest
import com.tripian.one.api.users.model.UserResponse
import com.tripian.one.network.TConfig
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TUsers

internal class TUsers {

    private val service: TUsers by lazy { TNetwork.createService() }

    suspend fun refreshToken(token: String?): RefreshTokenResponse {
        return service.refreshToken(RefreshTokenRequest().apply {
            this.refreshToken = token
            this.device = TConfig.device
        })
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        val res = service.login(request.apply {
            device = TConfig.device
        })

        TokenManager.tokenReceived(res.data)

        return res
    }

    suspend fun socialLogin(): EmptyResponse {
        val res = service.socialLogin(SocialLoginRequest().apply {
            device = TConfig.device
        })

        return res
    }

    suspend fun guestLogin(request: GuestLoginRequest): LoginResponse {
        val res = service.guestLogin(
            request.apply {
                device = TConfig.device
            }
        )

        TokenManager.tokenReceived(res.data)

        return res
    }

    suspend fun lightLogin(request: LightLoginRequest): LoginResponse {
        val res = service.lightLogin(
            request.apply {
                device = TConfig.device
            }
        )

        TokenManager.tokenReceived(res.data)

        return res
    }

    suspend fun logout(): EmptyResponse {
        return service.logout()
    }

    suspend fun register(request: RegisterRequest): LoginResponse {
        val res = service.register(request.apply {
            device = TConfig.device
        })

        TokenManager.tokenReceived(res.data)

        return res
    }

    suspend fun sendMail(request: ForgotPasswordRequest): EmptyResponse {
        return service.sendMail(request)
    }

    suspend fun resetPassword(request: ForgotPasswordRequest): EmptyResponse {
        return service.resetPassword(request)
    }

    suspend fun getUser(): UserResponse {
        return service.getUser()
    }

    suspend fun updateUser(request: UpdateUserRequest): UserResponse {
        return service.updateUser(request)
    }

    suspend fun deleteUser(): EmptyResponse {
        return service.deleteUser()
    }
}