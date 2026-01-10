package com.tripian.one.network.service

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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Timeline API Service Interface
 * Retrofit definitions for Timeline endpoints
 */
internal interface TTimelineService {

    /**
     * Create a new timeline
     * POST /timeline
     *
     * @param settings TimelineSettings with creation parameters
     * @return TimelineResponse containing the created Timeline
     */
    @POST("timeline")
    suspend fun createTimeline(@Body settings: TimelineSettings): TimelineResponse

    /**
     * Get timeline by hash
     * GET /timeline/{hash}
     *
     * @param hash Timeline hash identifier
     * @return TimelineResponse containing the Timeline
     */
    @GET("timeline/{hash}")
    suspend fun getTimeline(@Path("hash") hash: String): TimelineResponse

    /**
     * Edit a segment in the timeline
     * PUT /timeline/{hash}
     *
     * @param hash Timeline hash identifier
     * @param segment TimelineSegmentSettings with updated values
     * @return TimelineGenericResponse
     */
    @PUT("timeline/{hash}")
    suspend fun editSegment(
        @Path("hash") hash: String,
        @Body segment: TimelineSegmentSettings
    ): TimelineGenericResponse

    /**
     * Delete a timeline
     * DELETE /timeline/{hash}
     *
     * @param hash Timeline hash identifier
     * @return TimelineDeleteResponse
     */
    @DELETE("timeline/{hash}")
    suspend fun deleteTimeline(@Path("hash") hash: String): TimelineDeleteResponse

    /**
     * Delete a segment from the timeline
     * DELETE /timeline/{hash}/{segmentIndex}
     *
     * @param hash Timeline hash identifier
     * @param segmentIndex Index of the segment to delete
     * @return TimelineDeleteResponse
     */
    @DELETE("timeline/{hash}/{segmentIndex}")
    suspend fun deleteSegment(
        @Path("hash") hash: String,
        @Path("segmentIndex") segmentIndex: Int
    ): TimelineDeleteResponse

    /**
     * Get timeline plans for a specific plan ID
     * GET /timeline/segment/{planId}
     *
     * @param planId Plan identifier
     * @return TimelinePlansResponse containing list of TimelinePlan
     */
    @GET("timeline/segment/{planId}")
    suspend fun getTimelinePlans(@Path("planId") planId: String): TimelinePlansResponse

    /**
     * Get user's timelines with optional filters
     * GET /timeline
     *
     * @param dateFrom Optional start date filter (YYYY-MM-DD)
     * @param dateTo Optional end date filter (YYYY-MM-DD)
     * @param limit Maximum number of results (default 100)
     * @return TimelinesResponse containing list of Timeline
     */
    @GET("timeline")
    suspend fun getUserTimelines(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("limit") limit: Int? = 100
    ): TimelinesResponse

    /**
     * Add a new step to a timeline plan
     * POST /timeline/steps
     *
     * @param step TimelineStepCreateRequest with step details
     * @return TimelineStepResponse containing the created TimelineStep
     */
    @POST("timeline/steps")
    suspend fun addTimelineStep(@Body step: TimelineStepCreateRequest): TimelineStepResponse

    /**
     * Edit an existing timeline step
     * PUT /timeline/steps/{stepId}
     *
     * @param stepId Step identifier
     * @param step TimelineStepEditRequest with updated values
     * @return TimelineStepResponse containing the updated TimelineStep
     */
    @PUT("timeline/steps/{stepId}")
    suspend fun editTimelineStep(
        @Path("stepId") stepId: Int,
        @Body step: TimelineStepEditRequest
    ): TimelineStepResponse

    /**
     * Delete a timeline step
     * DELETE /timeline/steps/{stepId}
     *
     * @param stepId Step identifier
     * @return TimelineDeleteResponse
     */
    @DELETE("timeline/steps/{stepId}")
    suspend fun deleteTimelineStep(@Path("stepId") stepId: Int): TimelineDeleteResponse
}
