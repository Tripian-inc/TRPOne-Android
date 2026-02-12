package com.tripian.one.api.cities.model

import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.util.BaseResponse
import java.io.Serializable

// ------------ request ------------

data class CityResolveRequestItem(
    val coordinate: Coordinate? = null,
    val cityName: String? = null,
    val countryName: String? = null
) : Serializable

// ------------ response ------------

class GetCitiesResponse : BaseResponse() {
    var data: List<City>? = null
    var pagination: Pagination? = null
}

class GetCityResponse : BaseResponse() {
    var data: City? = null
}

class CityResolveResponse : BaseResponse() {
    var data: List<CityResolveData>? = null
}

data class CityResolveData(
    val cityId: Int?,
    val coordinate: Coordinate?,
    val cityName: String?,
    val countryName: String?
) : Serializable