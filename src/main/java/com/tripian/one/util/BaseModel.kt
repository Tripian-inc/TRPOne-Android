package com.tripian.one.util

import java.io.Serializable

open class BaseRequest : Serializable

open class BaseResponse : Serializable {
    var status: Int = 0
    var success: Boolean = false
    var message: String = ""
}