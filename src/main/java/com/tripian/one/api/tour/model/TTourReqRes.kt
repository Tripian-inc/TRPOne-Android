package com.tripian.one.api.tour.model

import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.network.TConfig
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------

/**
 * Tour Search Request Model
 * POST /tour-api/search
 */
class TourSearchRequest : BaseRequest() {
    // Required
    var cityId: Int = 0

    // Optional Search Filters
    var lat: Double? = null
    var lng: Double? = null
    var instantAvailability: Int = 1  // 0 or 1
    var providerId: Int? = null
    var keywords: String? = null
    var tagIds: String? = null  // Comma-separated
    var minPrice: Int? = null   // Int (not Double)
    var maxPrice: Int? = null   // Int (not Double)
    var adults: Int? = null
    var currency: String? = null  // "USD", "EUR"
    var date: String? = null      // "YYYY-MM-DD"
    var hour: String? = null
    var minRating: Double? = null
    var minDuration: Int? = null  // Minutes
    var maxDuration: Int? = null  // Minutes
    var lang: String? = null

    // Pagination
    var offset: Int = 0
    var limit: Int = 10

    // Location & Sorting
    var radius: Double? = null    // km
    var sortingBy: String? = null // "price", "rating"
    var sortingType: String? = null // "asc", "desc"

    companion object {
        fun create(
            cityId: Int,
            lat: Double? = null,
            lng: Double? = null,
            instantAvailability: Int? = null,
            providerId: Int? = null,
            keywords: String? = null,
            tagIds: String? = null,
            minPrice: Int? = null,
            maxPrice: Int? = null,
            adults: Int? = null,
            currency: String? = null,
            date: String? = null,
            hour: String? = null,
            minRating: Double? = null,
            minDuration: Int? = null,
            maxDuration: Int? = null,
            lang: String? = null,
            offset: Int = 0,
            limit: Int = 30,
            radius: Double? = null,
            sortingBy: String? = null,
            sortingType: String? = null
        ): TourSearchRequest {
            return TourSearchRequest().apply {
                this.cityId = cityId
                this.lat = lat
                this.lng = lng
                this.instantAvailability = instantAvailability ?: 1
                this.providerId = providerId
                this.keywords = keywords
                this.tagIds = tagIds
                this.minPrice = minPrice
                this.maxPrice = maxPrice
                this.adults = adults
                this.currency = currency ?: TConfig.currency
                this.date = date
                this.hour = hour
                this.minRating = minRating
                this.minDuration = minDuration
                this.maxDuration = maxDuration
                this.lang = lang
                this.offset = offset
                this.limit = limit
                this.radius = radius
                this.sortingBy = sortingBy
                this.sortingType = sortingType
            }
        }
    }
}

/**
 * Tour Schedule Request Model
 * POST /tour-api/{productId}/schedule
 */
class TourScheduleRequest : BaseRequest() {
    var date: String = ""        // Required - "YYYY-MM-DD" (range start when `to` is set)
    var to: String? = null       // Optional - "YYYY-MM-DD" range end; when set, response uses `dates` buckets
    var currency: String? = null // Optional - "USD"
    var lang: String? = null     // Optional - "en"

    companion object {
        fun create(
            date: String,
            to: String? = null,
            currency: String? = null,
            lang: String? = null
        ): TourScheduleRequest {
            return TourScheduleRequest().apply {
                this.date = date
                this.to = to
                this.currency = currency ?: TConfig.currency
                this.lang = lang
            }
        }
    }
}

// ------------ response ------------

/**
 * Tour Search Response
 */
class TourSearchResponse : BaseResponse() {
    var data: TourSearchData? = null
    var pagination: Pagination? = null
    var UUID: String? = null
}

/**
 * Tour Schedule Response
 */
class TourScheduleResponse : BaseResponse() {
    var data: TourSchedule? = null
    var UUID: String? = null
}

// ------------ schedule availability ------------

/**
 * `tour-api/schedule-availability` — batch availability lookup across multiple
 * activities for a single target date.
 */
class TourScheduleAvailabilityRequest : BaseRequest() {
    /** Activity IDs to check, e.g. ["C_163295_15", ...] */
    var items: List<String> = emptyList()

    /** Target date "YYYY-MM-DD" */
    var date: String = ""

    /** Currency code (e.g., "EUR"). */
    var currency: String? = null

    /** Language code (e.g., "en"). */
    var lang: String? = null

    companion object {
        fun create(
            items: List<String>,
            date: String,
            currency: String? = null,
            lang: String? = null
        ): TourScheduleAvailabilityRequest {
            return TourScheduleAvailabilityRequest().apply {
                this.items = items
                this.date = date
                this.currency = currency ?: TConfig.currency
                this.lang = lang
            }
        }
    }
}

class TourScheduleAvailabilityResponse : BaseResponse() {
    var data: TourScheduleAvailabilityData? = null
    var UUID: String? = null
}

// ------------ product lookup ------------

/**
 * `tour-api/product-lookup` — fetch a single tour product by `providerId + productId`.
 */
class TourProductLookupResponse : BaseResponse() {
    var data: TourProductLookupData? = null
    var UUID: String? = null
}
