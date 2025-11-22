package com.tripian.one.network.service

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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface TUsers {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/login-social")
    suspend fun socialLogin(@Body request: SocialLoginRequest): EmptyResponse

    @POST("auth/guest-login")
    suspend fun guestLogin(@Body request: GuestLoginRequest): LoginResponse

    @POST("auth/light-register-login")
    suspend fun lightLogin(@Body request: LightLoginRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): EmptyResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    @POST("auth/reset-password")
    suspend fun sendMail(@Body request: ForgotPasswordRequest): EmptyResponse

    @PUT("auth/reset-password")
    suspend fun resetPassword(@Body request: ForgotPasswordRequest): EmptyResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): RefreshTokenResponse

    @GET("user")
    suspend fun getUser(): UserResponse

    @PUT("user")
    suspend fun updateUser(@Body request: UpdateUserRequest): UserResponse

    @DELETE("user")
    suspend fun deleteUser(): EmptyResponse
}