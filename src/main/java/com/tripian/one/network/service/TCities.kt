package com.tripian.one.network.service

import com.tripian.one.api.cities.model.GetCitiesResponse
import com.tripian.one.api.cities.model.GetCityResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TCities {

    @GET("cities")
    suspend fun cities(
        @Query("search") search: String?, @Query("countryCode") countryCode: String?,
        @Query("page") page: Int?, @Query("limit") limit: Int?
    ): GetCitiesResponse

    @GET("cities/{cityId}")
    suspend fun city(@Path("cityId") cityId: Int): GetCityResponse
}