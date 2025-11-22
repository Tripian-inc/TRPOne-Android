package com.tripian.one.api.cities.model

import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.util.BaseResponse

// ------------ response ------------

class GetCitiesResponse : BaseResponse() {
    var data: List<City>? = null
    var pagination: Pagination? = null
}

class GetCityResponse : BaseResponse() {
    var data: City? = null
}