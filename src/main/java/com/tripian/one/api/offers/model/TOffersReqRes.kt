package com.tripian.one.api.offers.model

import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------

class AddOfferRequest : BaseRequest() {
    var optInDate: String? = null
}

// ------------ response ------------

class OffersResponse : BaseResponse() {
    var data: List<Offer>? = null
    var pagination: Pagination? = null
}

class OfferResponse : BaseResponse() {
    var data: AddOfferResponse? = null
}

class AddOfferResponse : BaseResponse() {
    var updated: Boolean = false
}