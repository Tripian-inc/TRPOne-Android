package com.tripian.one.api.timeline.model

import com.google.gson.annotations.SerializedName
import com.tripian.one.api.cities.model.City
import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.Poi
import com.tripian.one.api.trip.model.Accommodation
import java.io.Serializable

/**
 * Timeline Model - Main timeline/trip representation
 */
class Timeline : Serializable {
    var id: Int = 0
    var tripHash: String = ""
    var tripType: Int? = null
    var tripProfile: TimelineProfile? = null
    var city: City? = null
    var plans: List<TimelinePlan>? = null
}

/**
 * Timeline Profile - User preferences and settings for the timeline
 */
class TimelineProfile : Serializable {
    var cityId: Int = 0
    var hash: String? = null
    var adults: Int = 1
    var children: Int? = null
    var pets: Int? = null
    var answerIds: List<Int>? = null
    var doNotRecommend: List<String>? = null
    var excludePoiIds: List<String>? = null  // String (not Int)
    var excludeHashPois: List<String>? = null
    var considerWeather: Boolean = false
    var segments: List<TimelineSegment>? = null
}

/**
 * Timeline Segment - A segment of the timeline (e.g., a day or activity block)
 */
class TimelineSegment : Serializable {
    var available: Boolean = true
    var title: String? = null
    var description: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var coordinate: Coordinate? = null
    var destinationCoordinate: Coordinate? = null
    var adults: Int = 1
    var children: Int = 0
    var pets: Int = 0
    var cityId: Int? = null
    var generatedStatus: Int? = null
    var answerIds: List<Int>? = null
    var doNotRecommend: List<String>? = null
    var excludePoiIds: List<String>? = null  // String (not Int)
    var includePoiIds: List<String>? = null  // String (not Int)
    var dayIds: List<Int>? = null
    var considerWeather: Boolean = false
    var distinctPlan: Boolean = false
    var accommodation: Accommodation? = null
    var destinationAccommodation: Accommodation? = null

    // New fields from iOS update
    var smartRecommendation: Boolean? = null
    var activityFreeText: String? = null
    var activityIds: List<String>? = null
    var excludedActivityIds: List<String>? = null
    var segmentType: String? = null  // "itinerary", "booked_activity", "generated"
    var additionalData: TimelineSegmentAdditionalData? = null
}

/**
 * Timeline Segment Additional Data - Extra data for booked activities
 */
class TimelineSegmentAdditionalData : Serializable {
    var activityId: String? = null
    var bookingId: String? = null
    var title: String? = null
    var imageUrl: String? = null
    var description: String? = null
    var startDatetime: String? = null
    var endDatetime: String? = null
    var coordinate: Coordinate? = null
    var cancellation: String? = null
    var price: Double? = null
    var currency: String? = null   // New field
    var duration: Double? = null   // New field
}

/**
 * Timeline Plan - A daily plan within the timeline
 */
class TimelinePlan : Serializable {
    var id: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var generatedStatus: Int = 0
    var available: Boolean? = null
    var tripType: Int? = null
    var name: String? = null
    var description: String? = null
    var adults: Int? = null
    var children: Int? = null
    var pets: Int? = null
    var city: City? = null
    var steps: List<TimelineStep>? = null
}

/**
 * Timeline Step - A step/activity within a plan
 */
class TimelineStep : Serializable {
    var id: Int = 0
    var poi: Poi? = null
    var order: Int = 0
    var planId: String? = null
    var score: Double? = null
    var startDateTimes: String? = null
    var endDateTimes: String? = null
    var stepType: String? = null
    var warningMessage: List<String>? = null
    var alternatives: List<String>? = null
}

/**
 * Custom POI - For adding custom points of interest
 */
class CustomPoi : Serializable {
    var name: String? = null
    var coordinate: Coordinate? = null
    var address: String? = null
    var description: String? = null
    var tags: List<String>? = null
    var phone: String? = null
    var web: String? = null
    var categoryId: Int? = null

    companion object {
        fun create(
            name: String? = null,
            coordinate: Coordinate? = null,
            address: String? = null,
            description: String? = null,
            tags: List<String>? = null,
            phone: String? = null,
            web: String? = null,
            categoryId: Int? = null
        ): CustomPoi {
            return CustomPoi().apply {
                this.name = name
                this.coordinate = coordinate
                this.address = address
                this.description = description
                this.tags = tags
                this.phone = phone
                this.web = web
                this.categoryId = categoryId
            }
        }
    }
}

/**
 * Extension function to check if timeline is generated
 */
fun Timeline?.isGenerated(): Boolean {
    if (this == null) return false
    return this.plans != null && !(this.plans!!.any { p -> p.generatedStatus == 0 })
}

/**
 * Extension function to check if plan is generated
 */
fun TimelinePlan?.isGenerated(): Boolean {
    if (this == null) return false
    return this.generatedStatus != 0
}

/**
 * Generated Status values
 * 0: Not generated yet
 * 1: Generated with POIs
 * -1: Generated without POIs
 */
object GeneratedStatus {
    const val NOT_GENERATED = 0
    const val GENERATED_WITH_POIS = 1
    const val GENERATED_WITHOUT_POIS = -1
}

/**
 * Segment Type values
 */
object SegmentType {
    const val ITINERARY = "itinerary"
    const val BOOKED_ACTIVITY = "booked_activity"
    const val GENERATED = "generated"
}
