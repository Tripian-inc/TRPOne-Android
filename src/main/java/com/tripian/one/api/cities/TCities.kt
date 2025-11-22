package com.tripian.one.api.cities

import com.tripian.one.api.cities.model.GetCitiesResponse
import com.tripian.one.api.cities.model.GetCityResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TCities

internal class TCities {

    private val service: TCities by lazy { TNetwork.createService() }

    suspend fun cities(search: String?, countryCode: String?, page: Int?, limit: Int?): GetCitiesResponse {
        return service.cities(search, countryCode, page, limit)
    }

    suspend fun city(cityId: Int): GetCityResponse {
        return service.city(cityId)
    }
}