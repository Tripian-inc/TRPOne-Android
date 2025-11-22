package com.tripian.one.network.service

import com.tripian.one.api.companion.model.CompanionRequest
import com.tripian.one.api.companion.model.CompanionResponse
import com.tripian.one.api.companion.model.CompanionsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import retrofit2.http.*

internal interface TCompanions {

    @GET("companions")
    suspend fun companions(@Query("page") page: Int?, @Query("limit") limit: Int?): CompanionsResponse

    @POST("companions")
    suspend fun addCompanion(@Body request: CompanionRequest): CompanionResponse

    @PUT("companions/{companionId}")
    suspend fun updateCompanion(@Path("companionId") companionId: Int, @Body request: CompanionRequest): CompanionResponse

    @DELETE("companions/{companionId}")
    suspend fun deleteCompanion(@Path("companionId") companionId: Int): DeleteResponse
}