package com.tripian.one.api.offers

import com.tripian.one.api.offers.model.AddOfferRequest
import com.tripian.one.api.offers.model.OfferResponse
import com.tripian.one.api.offers.model.OffersResponse
import com.tripian.one.api.pois.model.PoisResponse
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TOffers

internal class TOffers {
    private val service: TOffers by lazy { TNetwork.createService() }

    suspend fun addOffer(offerId: Int, request: AddOfferRequest): OfferResponse {
        return service.addOffer(offerId, request)
    }

    suspend fun deleteOffer(offerId: Int): DeleteResponse {
        return service.deleteOffer(offerId = offerId)
    }

    suspend fun getOffers(
        dateFrom: String? = null,
        dateTo: String? = null,
        poiIds: String? = null,
        typeId: String? = null,
        boundary: String? = null,
        excludeOptIn: Int? = null
    ): OffersResponse {
        return service.getOffers(dateFrom, dateTo, poiIds, typeId, boundary, excludeOptIn)
    }

    suspend fun getMyOffers(
        dateFrom: String? = null,
        dateTo: String? = null
    ): PoisResponse {
        return service.getMyOffers(dateFrom, dateTo)
    }

    suspend fun getPoisWithOffer(
        dateFrom: String? = null,
        dateTo: String? = null,
        boundary: String? = null
    ): PoisResponse {
        return service.getPoisWithOffer(dateFrom, dateTo, boundary)
    }
}