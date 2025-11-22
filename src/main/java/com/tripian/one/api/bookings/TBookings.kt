package com.tripian.one.api.bookings

import com.tripian.one.api.bookings.model.ReservationRequest
import com.tripian.one.api.bookings.model.ReservationResponse
import com.tripian.one.api.bookings.model.ReservationsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TBookings

internal class TBookings {

    private val service: TBookings by lazy { TNetwork.createService() }

    suspend fun bookings(
        provider: String?, tripHash: String?, cityId: Int?, poiId: String?,
        dateFrom: String?, dateTo: String?, page: Int?, limit: Int?
    ): ReservationsResponse {
        return service.bookings(provider, tripHash, cityId, poiId, dateFrom, dateTo, page, limit)
    }

    suspend fun addBookings(request: ReservationRequest): ReservationResponse {
        return service.addBookings(request)
    }

    suspend fun deleteBookings(bookingId: Int): DeleteResponse {
        return service.deleteBookings(bookingId)
    }
}