package com.tripian.one.network.service

import com.tripian.one.api.tour.model.TourScheduleRequest
import com.tripian.one.api.tour.model.TourScheduleResponse
import com.tripian.one.api.tour.model.TourSearchRequest
import com.tripian.one.api.tour.model.TourSearchResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Tour API Service Interface
 * Retrofit definitions for Tour endpoints
 */
internal interface TToursService {

    /**
     * Search tours
     * POST /tour-api/search
     *
     * @param request TourSearchRequest with search parameters
     * @return TourSearchResponse containing list of TourProduct
     */
    @POST("tour-api/search")
    suspend fun searchTours(@Body request: TourSearchRequest): TourSearchResponse

    /**
     * Get tour schedule/availability
     * POST /tour-api/{productId}/schedule
     *
     * @param productId Tour product ID
     * @param request TourScheduleRequest with date and optional currency/lang
     * @return TourScheduleResponse containing TourSchedule with slots
     */
    @POST("tour-api/{productId}/schedule")
    suspend fun getTourSchedule(
        @Path("productId") productId: String,
        @Body request: TourScheduleRequest
    ): TourScheduleResponse
}
