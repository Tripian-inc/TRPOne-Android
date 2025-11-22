package com.tripian.one.network.service

import com.tripian.one.api.offers.model.AddOfferRequest
import com.tripian.one.api.offers.model.OfferResponse
import com.tripian.one.api.offers.model.OffersResponse
import com.tripian.one.api.pois.model.PoisResponse
import com.tripian.one.api.trip.model.DeleteResponse
import retrofit2.http.*

internal interface TOffers {

    @PUT("offers/{offerId}")
    suspend fun addOffer(
        @Path("offerId") offerId: Int,
        @Body request: AddOfferRequest
    ): OfferResponse

    @GET("offers")
    suspend fun getOffers(
        @Query("dateFrom") dateFrom: String?, @Query("dateTo") dateTo: String?,
        @Query("poiIds") poiIds: String?, @Query("typeId") typeId: String?,
        @Query("boundary") boundary: String?, @Query("excludeOptIn") excludeOptIn: Int?
    ): OffersResponse

    @DELETE("offers/{offerId}")
    suspend fun deleteOffer(@Path("offerId") offerId: Int): DeleteResponse

    @GET("offers/opt-in")
    suspend fun getMyOffers(
        @Query("dateFrom") dateFrom: String?, @Query("dateTo") dateTo: String?
    ): PoisResponse

    @GET("offers/pois")
    suspend fun getPoisWithOffer(
        @Query("dateFrom") dateFrom: String?, @Query("dateTo") dateTo: String?,
        @Query("boundary") boundary: String?
    ): PoisResponse
}