package com.tripian.one.network.service

import com.tripian.one.api.pois.model.PoiCategoriesResponse
import com.tripian.one.api.pois.model.PoiResponse
import com.tripian.one.api.pois.model.PoisResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TPois {

    @GET("poi-categories")
    suspend fun poiCategories(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query("version") version: Int?
    ): PoiCategoriesResponse

    @GET("pois")
    suspend fun getPoi(
        @Query("cityId") cityId: Int? = null,
        @Query("search") search: String? = null,
        @Query("coordinate") coordinate: String? = null,
        @Query("poiIds") poiIds: String? = null,
        @Query("mustTryIds") mustTryIds: String? = null,
        @Query("poiCategories") categoryIds: String? = null,
        @Query("distance") distance: Double? = null,
        @Query("bounds") boundary: String? = null,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
        @Query("price") price: String? = null,
        @Query("rating") rating: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
    ): PoisResponse

    @GET("pois/{poiId}")
    suspend fun getPoiDetail(@Path("poiId") poiId: String): PoiResponse
}