package com.tripian.one.api.reactions.model

import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------

class ReactionRequest : BaseRequest() {
    var id: Int? = null
    var poiId: String? = null
    var stepId: Int? = null
    var tripHash: String? = null
    var reaction: String? = null
    var comment: String? = null
}

// ------------ response ------------

class ReactionsResponse : BaseResponse() {
    var data: List<Reaction>? = null
    var pagination: Pagination? = null
}

class ReactionResponse : BaseResponse() {
    var data: Reaction? = null
}