package com.tripian.one.api.tour.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Tour Product model representing a tour/activity
 */
class TourProduct : Serializable {
    @SerializedName("product_id")
    var productId: String = ""

    @SerializedName("provider_id")
    var providerId: Int = 0

    var id: String = ""

    @SerializedName("city_id")
    var cityId: Int = 0

    var title: String = ""

    var description: String? = null

    var url: String? = null

    var price: Double? = null

    var currency: String? = null

    @SerializedName("current_price")
    var currentPrice: Double? = null

    var duration: Double? = null

    var rating: Double? = null

    @SerializedName("rating_count")
    var ratingCount: Int? = null

    var status: Int? = null

    var available: Boolean? = null

    var version: String? = null

    var images: List<TourImage>? = null

    @SerializedName("location_names")
    var locationNames: List<String>? = null

    @SerializedName("tag_ids")
    var tagIds: List<Int>? = null

    var tags: List<String>? = null

    var locations: List<TourLocation>? = null

    @SerializedName("tripian_pois")
    var tripianPois: List<String>? = null

    @SerializedName("distance_km")
    var distanceKm: Double? = null

    /**
     * Per-product availability hint returned by the search endpoint for the requested
     * date range. May be null if no date filter was applied. `time == null` on a slot
     * means a flexible (any-time) slot for that day.
     */
    var slots: List<TourSlot>? = null
}

/**
 * Tour Image model
 */
class TourImage : Serializable {
    @SerializedName("is_cover")
    var isCover: Boolean? = null

    var url: String? = null
}

/**
 * Tour Location model
 * Note: API uses "lon" not "lng"
 */
class TourLocation : Serializable {
    var lat: Double? = null
    var lon: Double? = null  // API uses "lon" not "lng"
}

/**
 * Search-time availability slot attached to a [TourProduct].
 *
 * - [date]   "YYYY-MM-DD"
 * - [time]   "HH:mm" — when **null** the slot is flexible (the activity has no fixed
 *            start time on [date]; booking screens render a flexible-time card instead
 *            of a time grid).
 */
class TourSlot : Serializable {
    var date: String? = null
    var time: String? = null
    var price: Double? = null

    @SerializedName("fullRefund")
    var fullRefund: Boolean? = null

    val isFlexible: Boolean
        get() = time == null
}

/**
 * Tour Schedule model.
 *
 * Two response shapes coexist:
 * 1. **Single-day**: top-level [date] populated and [slots] carries the day's slots.
 * 2. **Range**: [dates] populated with one [TourScheduleDay] per requested day; the
 *    top-level [slots] is typically empty in this case.
 *
 * Use [allSlots] to iterate slots uniformly regardless of which shape the server
 * returned.
 */
class TourSchedule : Serializable {
    var productId: String? = null
    var title: String? = null
    var date: String? = null
    var tags: List<String>? = null
    var duration: Double? = null

    /** Slots when the request was a single-day query. */
    var slots: List<TourScheduleSlot>? = null

    /** Per-day slot buckets when the request used a date range. */
    var dates: List<TourScheduleDay>? = null

    /** Convenience: every slot across every day. */
    val allSlots: List<TourScheduleSlot>
        get() {
            val perDay = dates?.flatMap { it.slots ?: emptyList() }.orEmpty()
            return if (perDay.isNotEmpty()) perDay else slots.orEmpty()
        }
}

/**
 * One day's worth of slots within a multi-day [TourSchedule].
 */
class TourScheduleDay : Serializable {
    var date: String? = null
    var slots: List<TourScheduleSlot>? = null
}

/**
 * Tour Schedule Slot model.
 *
 * - [time] "HH:mm" — when **null** the slot is flexible (any-time) for the parent day.
 * - [availableCount] populated by `tour-api/schedule-bulk` (remaining capacity).
 */
class TourScheduleSlot : Serializable {
    /** Often null inside per-day buckets; top-level [TourSchedule.date] carries the day. */
    var date: String? = null

    var time: String? = null

    var price: Double? = null

    var fullRefund: Boolean? = null

    var availableCount: Int? = null

    val isFlexible: Boolean
        get() = time == null
}

/**
 * Tour Search Data wrapper
 */
class TourSearchData : Serializable {
    var products: List<TourProduct>? = null

    var total: Int? = null

    var limit: Int? = null

    var offset: Int? = null

    /** Faceted search metadata grouped by provider. */
    var facets: List<TourFacet>? = null
}

// ------------ facets ------------

/**
 * Faceted search metadata grouped by provider.
 */
class TourFacet : Serializable {
    var provider: String? = null
    var source: String? = null
    var categories: List<TourFacetCategory>? = null
    var features: List<TourFacetFeature>? = null

    @SerializedName("price_range")
    var priceRange: TourFacetPriceRange? = null

    @SerializedName("duration_range")
    var durationRange: TourFacetDurationRange? = null
}

class TourFacetCategory : Serializable {
    var id: String? = null
    var key: String? = null
    var label: String? = null
    var icon: String? = null
    var count: Int? = null
}

class TourFacetFeature : Serializable {
    var id: String? = null
    var key: String? = null
    var label: String? = null
    var icon: String? = null
    var count: Int? = null
}

class TourFacetMoney : Serializable {
    var amount: Int? = null
    var currency: String? = null
}

class TourFacetPriceBounds : Serializable {
    var minimum: TourFacetMoney? = null
    var maximum: TourFacetMoney? = null
}

class TourFacetPriceRange : Serializable {
    var minimum: TourFacetMoney? = null
    var maximum: TourFacetMoney? = null
    var current: TourFacetPriceBounds? = null
    var absolute: TourFacetPriceBounds? = null
}

class TourFacetDurationBounds : Serializable {
    @SerializedName("minimum_minutes")
    var minimumMinutes: Int? = null

    @SerializedName("maximum_minutes")
    var maximumMinutes: Int? = null
}

class TourFacetDurationRange : Serializable {
    @SerializedName("minimum_minutes")
    var minimumMinutes: Int? = null

    @SerializedName("maximum_minutes")
    var maximumMinutes: Int? = null

    var current: TourFacetDurationBounds? = null
    var absolute: TourFacetDurationBounds? = null
}

// ------------ schedule availability ------------

/**
 * Single entry of the `tour-api/schedule-bulk` batch response — pairs the
 * requested `activityId` with its resolved schedule for the queried date.
 * `schedule == null` means the backend returned an empty/null schedule for that id
 * (typically: sold out or no slots on the requested day).
 */
class TourScheduleAvailabilityItem : Serializable {
    var id: String = ""
    var schedule: TourSchedule? = null

    /** Convenience: true when the schedule has at least one slot across all days. */
    val hasAvailability: Boolean
        get() = (schedule?.allSlots?.isNotEmpty() == true)
}

/** Wrapper for the availability batch response payload. */
class TourScheduleAvailabilityData : Serializable {
    var schedules: List<TourScheduleAvailabilityItem>? = null
}

// ------------ product lookup ------------

/**
 * `tour-api/product-lookup` returns a single fully populated product (coordinate,
 * city, category included) by `providerId + productId`. Used by the SDK to resolve
 * activities that the timeline doesn't already carry (e.g. no-location segments
 * whose city must be resolved from the product instead of from coordinates).
 */
class TourProductLookupData : Serializable {
    var product: TourProduct? = null
}
