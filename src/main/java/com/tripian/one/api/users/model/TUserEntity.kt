package com.tripian.one.api.users.model

import java.io.Serializable

data class Device constructor(
    var deviceId: String,
    var serviceToken: String? = null,
    var bundleId: String,
    var osVersion: String,
    var deviceOs: String
) : Serializable

class Token : Serializable {
    var idToken: String? = null
    var expiresIn: Int? = null
    var tokenType: String? = null
    var refreshToken: String? = null
}

class User : Serializable {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
    var dateOfBirth: String? = null
    var answers: List<Int>? = null
}