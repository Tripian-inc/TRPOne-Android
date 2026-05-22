package com.tripian.one.api.tour

import com.tripian.one.api.tour.model.TourProductLookupResponse
import com.tripian.one.api.tour.model.TourScheduleAvailabilityRequest
import com.tripian.one.api.tour.model.TourScheduleAvailabilityResponse
import com.tripian.one.api.tour.model.TourScheduleRequest
import com.tripian.one.api.tour.model.TourScheduleResponse
import com.tripian.one.api.tour.model.TourSearchRequest
import com.tripian.one.api.tour.model.TourSearchResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TToursService
import com.tripian.one.util.TLogger
import kotlinx.coroutines.delay
import retrofit2.HttpException

/**
 * Tour API Wrapper
 * Handles Tour Search and Schedule API calls with 504 retry logic
 */
internal class TTours {

    private val service: TToursService by lazy { TNetwork.createService() }

    companion object {
        private const val TAG = "TTours"
        private const val MAX_RETRIES = 1
        private const val RETRY_DELAY_MS = 1000L
    }

    /**
     * Search tours with automatic 504 retry logic
     */
    suspend fun searchTours(
        request: TourSearchRequest,
        retryCount: Int = 0
    ): TourSearchResponse {
        return try {
            service.searchTours(request)
        } catch (e: HttpException) {
            if (e.code() == 504 && retryCount < MAX_RETRIES) {
                TLogger.log("$TAG: Tour search received 504 error, retrying (${retryCount + 1}/$MAX_RETRIES)...")
                delay(RETRY_DELAY_MS)
                searchTours(request, retryCount + 1)
            } else {
                throw e
            }
        }
    }

    /**
     * Get tour schedule/availability for a specific product
     */
    suspend fun getTourSchedule(
        productId: String,
        request: TourScheduleRequest
    ): TourScheduleResponse {
        return service.getTourSchedule(productId, request)
    }

    /**
     * Lookup a single tour product by provider + product id.
     */
    suspend fun lookupTourProduct(
        providerId: Int,
        productId: String
    ): TourProductLookupResponse {
        return service.lookupTourProduct(providerId, productId)
    }

    /**
     * Batch availability lookup for multiple activities on a single date.
     */
    suspend fun getTourScheduleAvailability(
        request: TourScheduleAvailabilityRequest
    ): TourScheduleAvailabilityResponse {
        return service.getTourScheduleAvailability(request)
    }
}
