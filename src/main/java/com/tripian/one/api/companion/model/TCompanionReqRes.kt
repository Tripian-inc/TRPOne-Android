package com.tripian.one.api.companion.model

import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------
class CompanionRequest : BaseRequest() {
    var name: String? = null
    var title: String? = null
    var age: Int? = null
    var answers: Array<Int>? = null
}

// ------------ response ------------

class CompanionsResponse : BaseResponse() {
    var data: ArrayList<Companion>? = null
}

class CompanionResponse : BaseResponse() {
    var data: Companion? = null
}