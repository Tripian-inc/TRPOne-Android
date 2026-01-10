package com.tripian.one.api.tour

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
     *
     * @param request TourSearchRequest with search parameters
     * @param retryCount Current retry count (used internally)
     * @return TourSearchResponse
     * @throws HttpException if request fails after retries
     */
    suspend fun searchTours(
        request: TourSearchRequest,
        retryCount: Int = 0
    ): TourSearchResponse {
        return try {
            service.searchTours(request)
        } catch (e: HttpException) {
            // Check for 504 Gateway Timeout and retry if within limit
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
     *
     * @param productId Tour product ID
     * @param request TourScheduleRequest with date and optional parameters
     * @return TourScheduleResponse
     */
    suspend fun getTourSchedule(
        productId: String,
        request: TourScheduleRequest
    ): TourScheduleResponse {
        return service.getTourSchedule(productId, request)
    }
}
