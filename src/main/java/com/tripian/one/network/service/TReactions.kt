package com.tripian.one.network.service

import com.tripian.one.api.reactions.model.ReactionRequest
import com.tripian.one.api.reactions.model.ReactionResponse
import com.tripian.one.api.reactions.model.ReactionsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import retrofit2.http.*

internal interface TReactions {

    @POST("reactions")
    suspend fun addReaction(@Body request: ReactionRequest): ReactionResponse

    @PUT("reactions/{reactionId}")
    suspend fun updateReaction(@Path("reactionId") reactionId: Int, @Body request: ReactionRequest): ReactionResponse

    @GET("reactions")
    suspend fun reactions(
        @Query("reaction") reaction: String?, @Query("tripHash") tripHash: String?,
        @Query("page") page: Int?, @Query("limit") limit: Int?
    ): ReactionsResponse

    @DELETE("reactions/{reactionId}")
    suspend fun deleteReaction(@Path("reactionId") reactionId: Int): DeleteResponse
}