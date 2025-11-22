package com.tripian.one.network.service

import com.tripian.one.api.bookings.model.ReservationRequest
import com.tripian.one.api.bookings.model.ReservationResponse
import com.tripian.one.api.bookings.model.ReservationsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import retrofit2.http.*

internal interface TBookings {

    @GET("bookings")
    suspend fun bookings(
        @Query("provider") provider: String?, @Query("tripHash") tripHash: String?,
        @Query("cityId") cityId: Int?, @Query("poiId") poiId: String?,
        @Query("dateFrom") dateFrom: String?, @Query("dateTo") dateTo: String?,
        @Query("page") page: Int?, @Query("limit") limit: Int?
    ): ReservationsResponse

    @POST("bookings")
    suspend fun addBookings(@Body request: ReservationRequest): ReservationResponse

    @DELETE("bookings/{bookingId}")
    suspend fun deleteBookings(@Path("bookingId") bookingId: Int): DeleteResponse
}