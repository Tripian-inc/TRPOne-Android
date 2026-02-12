package com.tripian.one.api.timeline.model

import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.api.trip.model.Accommodation
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse
import java.io.Serializable

// ------------ request ------------

/**
 * Timeline Settings - Request model for creating a new timeline
 * POST /timeline
 */
class TimelineSettings : BaseRequest() {
    var cityId: Int? = null
    var adults: Int = 1
    var children: Int = 0
    var pets: Int = 0
    var answers: List<Int> = emptyList()
    var doNotRecommend: List<String>? = null
    var excludePoiIds: List<String>? = null  // String (not Int)
    var considerWeather: Boolean = false
    var segments: List<TimelineSegmentSettings>? = null
    var lang: String? = null

    companion object {
        fun create(
            cityId: Int? = null,
            adults: Int = 1,
            children: Int = 0,
            pets: Int = 0,
            answers: List<Int> = emptyList(),
            doNotRecommend: List<String>? = null,
            excludePoiIds: List<String>? = null,
            considerWeather: Boolean = false,
            segments: List<TimelineSegmentSettings>? = null
        ): TimelineSettings {
            return TimelineSettings().apply {
                this.cityId = cityId
                this.adults = adults
                this.children = children
                this.pets = pets
                this.answers = answers
                this.doNotRecommend = doNotRecommend
                this.excludePoiIds = excludePoiIds
                this.considerWeather = considerWeather
                this.segments = segments
            }
        }
    }
}

/**
 * Timeline Segment Settings - Request model for creating/editing segments
 * Used in both create timeline and edit segment operations
 */
class TimelineSegmentSettings : Serializable {
    // Request fields
    var segmentIndex: Int? = null
    var cityId: Int? = null
    var title: String? = null
    var description: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var adults: Int = 1
    var children: Int? = null
    var pets: Int? = null
    var coordinate: Coordinate? = null
    var destinationCoordinate: Coordinate? = null
    var answerIds: List<Int> = emptyList()
    var doNotRecommend: List<String>? = null
    var excludePoiIds: List<String>? = null  // String (not Int)
    var includePoiIds: List<String>? = null  // String (not Int)
    var doNotGenerate: Int = 0
    var considerWeather: Boolean? = null
    var distinctPlan: Boolean? = null
    var available: Boolean = true
    var hash: String? = null
    var accommodation: Accommodation? = null
    var destinationAccommodation: Accommodation? = null
    var currency: String? = null

    // Response fields (when reading from API)
    var smartRecommendation: Boolean? = null
    var activityFreeText: String? = null
    var activityIds: List<String>? = null
    var excludedActivityIds: List<String>? = null
    var generatedStatus: Int? = null
    var dayIds: List<Int>? = null
    var segmentType: String? = null
    var additionalData: TimelineSegmentAdditionalData? = null

    companion object {
        fun create(
            segmentIndex: Int? = null,
            cityId: Int? = null,
            title: String? = null,
            description: String? = null,
            startDate: String? = null,
            endDate: String? = null,
            adults: Int = 1,
            children: Int? = null,
            pets: Int? = null,
            coordinate: Coordinate? = null,
            destinationCoordinate: Coordinate? = null,
            answerIds: List<Int> = emptyList(),
            doNotRecommend: List<String>? = null,
            excludePoiIds: List<String>? = null,
            includePoiIds: List<String>? = null,
            doNotGenerate: Int = 0,
            considerWeather: Boolean? = null,
            distinctPlan: Boolean? = null,
            available: Boolean = true,
            hash: String? = null,
            accommodation: Accommodation? = null,
            destinationAccommodation: Accommodation? = null,
            currency: String? = null
        ): TimelineSegmentSettings {
            return TimelineSegmentSettings().apply {
                this.segmentIndex = segmentIndex
                this.cityId = cityId
                this.title = title
                this.description = description
                this.startDate = startDate
                this.endDate = endDate
                this.adults = adults
                this.children = children
                this.pets = pets
                this.coordinate = coordinate
                this.destinationCoordinate = destinationCoordinate
                this.answerIds = answerIds
                this.doNotRecommend = doNotRecommend
                this.excludePoiIds = excludePoiIds
                this.includePoiIds = includePoiIds
                this.doNotGenerate = doNotGenerate
                this.considerWeather = considerWeather
                this.distinctPlan = distinctPlan
                this.available = available
                this.hash = hash
                this.accommodation = accommodation
                this.destinationAccommodation = destinationAccommodation
                this.currency = currency
            }
        }
    }
}

/**
 * Timeline Step Create Request - For adding a new step to a plan
 * POST /timeline/steps
 */
class TimelineStepCreateRequest : BaseRequest() {
    var planId: Int = 0  // Required
    var poiId: String? = null
    var stepType: String? = null
    var customPoi: CustomPoi? = null
    var startTime: String? = null  // "HH:mm"
    var endTime: String? = null    // "HH:mm"
    var order: Int? = null
    var lang: String? = null

    companion object {
        fun create(
            planId: Int,
            poiId: String? = null,
            stepType: String? = null,
            customPoi: CustomPoi? = null,
            startTime: String? = null,
            endTime: String? = null,
            order: Int? = null
        ): TimelineStepCreateRequest {
            return TimelineStepCreateRequest().apply {
                this.planId = planId
                this.poiId = poiId
                this.stepType = stepType
                this.customPoi = customPoi
                this.startTime = startTime
                this.endTime = endTime
                this.order = order
            }
        }
    }
}

/**
 * Timeline Step Edit Request - For updating an existing step
 * PUT /timeline/steps/{stepId}
 */
class TimelineStepEditRequest : BaseRequest() {
    var poiId: String? = null
    var stepType: String? = null
    var customPoi: CustomPoi? = null
    var startTime: String? = null  // "HH:mm"
    var endTime: String? = null    // "HH:mm"
    var order: Int? = null
    var lang: String? = null

    companion object {
        fun create(
            poiId: String? = null,
            stepType: String? = null,
            customPoi: CustomPoi? = null,
            startTime: String? = null,
            endTime: String? = null,
            order: Int? = null
        ): TimelineStepEditRequest {
            return TimelineStepEditRequest().apply {
                this.poiId = poiId
                this.stepType = stepType
                this.customPoi = customPoi
                this.startTime = startTime
                this.endTime = endTime
                this.order = order
            }
        }
    }
}

// ------------ response ------------

/**
 * Timeline Response - Single timeline response
 */
class TimelineResponse : BaseResponse() {
    var data: Timeline? = null
    var UUID: String? = null
}

/**
 * Timelines Response - List of timelines response
 */
class TimelinesResponse : BaseResponse() {
    var data: List<Timeline>? = null
    var pagination: Pagination? = null
    var UUID: String? = null
}

/**
 * Timeline Plans Response - List of plans response
 */
class TimelinePlansResponse : BaseResponse() {
    var data: List<TimelinePlan>? = null
    var UUID: String? = null
}

/**
 * Timeline Step Response - Single step response
 */
class TimelineStepResponse : BaseResponse() {
    var data: TimelineStep? = null
    var UUID: String? = null
}

/**
 * Timeline Generic Response - For operations that don't return specific data
 * (edit segment, delete operations)
 */
class TimelineGenericResponse : BaseResponse() {
    var UUID: String? = null
}

/**
 * Timeline Delete Response - For delete operations
 */
class TimelineDeleteResponse : BaseResponse() {
    var UUID: String? = null
}
