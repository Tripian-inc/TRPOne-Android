package com.tripian.one.api.pois.model

import com.tripian.one.util.BaseResponse

// ------------ request ------------


// ------------ response ------------

class PoisResponse : BaseResponse() {
    var data: List<Poi>? = null
    var pagination: Pagination? = null
}

class PoiResponse : BaseResponse() {
    var data: Poi? = null
}

class PoiCategoriesResponse : BaseResponse() {
    var data: PoiCategoryModel? = null
}