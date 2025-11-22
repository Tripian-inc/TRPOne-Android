package com.tripian.one.network.service

import com.tripian.one.api.trip.model.AddCustomPoiStepRequest
import com.tripian.one.api.trip.model.AddStepRequest
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.api.trip.model.ExportPlanRequest
import com.tripian.one.api.trip.model.ExportPlanResponse
import com.tripian.one.api.trip.model.PlanResponse
import com.tripian.one.api.trip.model.QuestionsResponse
import com.tripian.one.api.trip.model.StepAlternativesResponse
import com.tripian.one.api.trip.model.StepResponse
import com.tripian.one.api.trip.model.TripRequest
import com.tripian.one.api.trip.model.TripResponse
import com.tripian.one.api.trip.model.TripsResponse
import com.tripian.one.api.trip.model.UpdatePlanRequest
import com.tripian.one.api.trip.model.UpdateStepRequest
import com.tripian.one.api.trip.model.UpdateStepTimeRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TTrips {

    @GET("plans/{planId}")
    suspend fun plan(@Path("planId") planId: Int): PlanResponse

    @POST("misc/export-itinerary")
    suspend fun exportPlan(@Body request: ExportPlanRequest): ExportPlanResponse

    @PUT("plans/{planId}")
    suspend fun updatePlan(
        @Path("planId") planId: Int,
        @Body request: UpdatePlanRequest
    ): PlanResponse

    @GET("trips")
    suspend fun trips(
        @Query("dateFrom") dateFrom: String?, @Query("dateTo") dateTo: String?,
        @Query("page") page: Int?, @Query("limit") limit: Int?
    ): TripsResponse

    @GET("trips/{tripHash}")
    suspend fun trip(@Path("tripHash") tripHash: String): TripResponse

    @POST("trips")
    suspend fun createTrip(@Body request: TripRequest): TripResponse

    @POST("trips/{tripHash}")
    suspend fun updateTrip(
        @Path("tripHash") tripHash: String,
        @Body request: TripRequest
    ): TripResponse

    @DELETE("trips/{tripHash}")
    suspend fun deleteTrip(@Path("tripHash") tripHash: String): DeleteResponse

    @POST("step/alternatives")
    suspend fun stepAlternatives(
        @Query("planId") planId: Int, @Query("stepId") stepId: Int,
        @Query("tripHash") tripHash: String
    ): StepAlternativesResponse

    @POST("steps")
    suspend fun addStep(@Body request: AddStepRequest): StepResponse

    @POST("steps")
    suspend fun addCustomPoiStep(@Body request: AddCustomPoiStepRequest): StepResponse

    @PUT("steps/{stepId}")
    suspend fun updateStep(
        @Path("stepId") stepId: Int,
        @Body request: UpdateStepRequest
    ): StepResponse

    @PUT("steps/{stepId}")
    suspend fun updateStepTime(
        @Path("stepId") stepId: Int,
        @Body request: UpdateStepTimeRequest
    ): StepResponse

    @DELETE("steps/{stepId}")
    suspend fun deleteStep(@Path("stepId") stepId: Int): DeleteResponse

    @GET("trip/questions")
    suspend fun questions(
        @Query("cityId") cityId: Int?,
        @Query("category") category: String?,
        @Query("languageCode") languageCode: String?
    ): QuestionsResponse
}