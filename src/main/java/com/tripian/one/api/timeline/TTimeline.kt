package com.tripian.one.api.timeline

import com.tripian.one.api.timeline.model.TimelineDeleteResponse
import com.tripian.one.api.timeline.model.TimelineGenericResponse
import com.tripian.one.api.timeline.model.TimelinePlansResponse
import com.tripian.one.api.timeline.model.TimelineResponse
import com.tripian.one.api.timeline.model.TimelineSegmentSettings
import com.tripian.one.api.timeline.model.TimelineSettings
import com.tripian.one.api.timeline.model.TimelineStepCreateRequest
import com.tripian.one.api.timeline.model.TimelineStepEditRequest
import com.tripian.one.api.timeline.model.TimelineStepResponse
import com.tripian.one.api.timeline.model.TimelinesResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TTimelineService

/**
 * Timeline API Wrapper
 * Handles all Timeline-related API calls
 */
internal class TTimeline {

    private val service: TTimelineService by lazy { TNetwork.createService() }

    /**
     * Create a new timeline
     *
     * @param settings TimelineSettings with creation parameters
     * @return TimelineResponse
     */
    suspend fun createTimeline(settings: TimelineSettings): TimelineResponse {
        return service.createTimeline(settings)
    }

    /**
     * Get timeline by hash
     *
     * @param hash Timeline hash identifier
     * @return TimelineResponse
     */
    suspend fun getTimeline(hash: String): TimelineResponse {
        return service.getTimeline(hash)
    }

    /**
     * Edit a segment in the timeline
     *
     * @param hash Timeline hash identifier
     * @param segment TimelineSegmentSettings with updated values
     * @return TimelineGenericResponse
     */
    suspend fun editSegment(
        hash: String,
        segment: TimelineSegmentSettings
    ): TimelineGenericResponse {
        return service.editSegment(hash, segment)
    }

    /**
     * Delete a timeline
     *
     * @param hash Timeline hash identifier
     * @return TimelineDeleteResponse
     */
    suspend fun deleteTimeline(hash: String): TimelineDeleteResponse {
        return service.deleteTimeline(hash)
    }

    /**
     * Delete a segment from the timeline
     *
     * @param hash Timeline hash identifier
     * @param segmentIndex Index of the segment to delete
     * @return TimelineDeleteResponse
     */
    suspend fun deleteSegment(hash: String, segmentIndex: Int): TimelineDeleteResponse {
        return service.deleteSegment(hash, segmentIndex)
    }

    /**
     * Get timeline plans for a specific plan ID
     *
     * @param planId Plan identifier
     * @return TimelinePlansResponse
     */
    suspend fun getTimelinePlans(planId: String): TimelinePlansResponse {
        return service.getTimelinePlans(planId)
    }

    /**
     * Get user's timelines with optional filters
     *
     * @param dateFrom Optional start date filter (YYYY-MM-DD)
     * @param dateTo Optional end date filter (YYYY-MM-DD)
     * @param limit Maximum number of results (default 100)
     * @return TimelinesResponse
     */
    suspend fun getUserTimelines(
        dateFrom: String? = null,
        dateTo: String? = null,
        limit: Int? = 100
    ): TimelinesResponse {
        return service.getUserTimelines(dateFrom, dateTo, limit)
    }

    /**
     * Add a new step to a timeline plan
     *
     * @param step TimelineStepCreateRequest with step details
     * @return TimelineStepResponse
     */
    suspend fun addTimelineStep(step: TimelineStepCreateRequest): TimelineStepResponse {
        return service.addTimelineStep(step)
    }

    /**
     * Edit an existing timeline step
     *
     * @param stepId Step identifier
     * @param step TimelineStepEditRequest with updated values
     * @return TimelineStepResponse
     */
    suspend fun editTimelineStep(
        stepId: Int,
        step: TimelineStepEditRequest
    ): TimelineStepResponse {
        return service.editTimelineStep(stepId, step)
    }

    /**
     * Delete a timeline step
     *
     * @param stepId Step identifier
     * @return TimelineDeleteResponse
     */
    suspend fun deleteTimelineStep(stepId: Int): TimelineDeleteResponse {
        return service.deleteTimelineStep(stepId)
    }
}
