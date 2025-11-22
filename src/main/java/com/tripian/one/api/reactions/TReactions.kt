package com.tripian.one.api.reactions

import com.tripian.one.api.reactions.model.ReactionRequest
import com.tripian.one.api.reactions.model.ReactionResponse
import com.tripian.one.api.reactions.model.ReactionsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TReactions

internal class TReactions {

    private val service: TReactions by lazy { TNetwork.createService() }

    suspend fun addReaction(request: ReactionRequest): ReactionResponse {
        return service.addReaction(request)
    }

    suspend fun updateReaction(reactionId: Int, request: ReactionRequest): ReactionResponse {
        return service.updateReaction(reactionId, request)
    }

    suspend fun deleteReaction(reactionId: Int): DeleteResponse {
        return service.deleteReaction(reactionId)
    }

    suspend fun reactions(reaction: String?, tripHash: String?, page: Int?, limit: Int?): ReactionsResponse {
        return service.reactions(reaction, tripHash, page, limit)
    }
}