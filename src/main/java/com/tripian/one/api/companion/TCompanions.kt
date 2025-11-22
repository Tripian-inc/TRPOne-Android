package com.tripian.one.api.companion

import com.tripian.one.api.companion.model.CompanionRequest
import com.tripian.one.api.companion.model.CompanionResponse
import com.tripian.one.api.companion.model.CompanionsResponse
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TCompanions

internal class TCompanions {

    private val service: TCompanions by lazy { TNetwork.createService() }

    suspend fun companions(page: Int?, limit: Int?): CompanionsResponse {
        return service.companions(page, limit)
    }

    suspend fun addCompanion(request: CompanionRequest): CompanionResponse {
        return service.addCompanion(request)
    }

    suspend fun updateCompanion(companionId: Int, request: CompanionRequest): CompanionResponse {
        return service.updateCompanion(companionId, request)
    }

    suspend fun deleteCompanion(companionId: Int): DeleteResponse {
        return service.deleteCompanion(companionId)
    }
}