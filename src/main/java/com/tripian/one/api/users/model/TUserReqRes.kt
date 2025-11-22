package com.tripian.one.api.users.model

import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------
class RefreshTokenRequest : BaseRequest() {
    var refreshToken: String? = null
    var device: Device? = null
}

class LoginRequest : BaseRequest() {
    var email: String? = null
    var password: String? = null
    var device: Device? = null
}

class SocialLoginRequest : BaseRequest() {
    var device: Device? = null
}

class GuestLoginRequest : BaseRequest() {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
    var device: Device? = null
}

class LightLoginRequest : BaseRequest() {
    var firstName: String? = null
    var lastName: String? = null
    var uniqueId: String? = null
    var device: Device? = null
}

class RegisterRequest : BaseRequest() {
    var firstName: String? = null
    var lastName: String? = null
    var email: String = ""
    var password: String = ""
    var dateOfBirth: String? = null
    var device: Device? = null
}

class UpdateUserRequest : BaseRequest() {
    var firstName: String? = null
    var lastName: String? = null
    var oldPassword: String? = null
    var password: String? = null
    var dateOfBirth: String? = null
    var answers: Array<Int>? = null
}

class ForgotPasswordRequest : BaseRequest() {
    var email: String? = null
    var password: String? = null
    var hash: String? = null
}

// ------------ response ------------

class RefreshTokenResponse : BaseResponse() {
    var data: Token? = null
}

class LoginResponse : BaseResponse() {
    var data: Token? = null
}

class UserResponse : BaseResponse() {
    var data: User? = null
}

class EmptyResponse : BaseResponse()