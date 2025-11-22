package com.tripian.one.api.trip

import com.tripian.one.api.trip.model.AddCustomPoiStepRequest
import com.tripian.one.api.trip.model.AddStepRequest
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.api.trip.model.ExportPlanRequest
import com.tripian.one.api.trip.model.ExportPlanResponse
import com.tripian.one.api.trip.model.PlanResponse
import com.tripian.one.api.trip.model.QuestionsResponse
import com.tripian.one.api.trip.model.StepAlternativesResponse
import com.tripian.one.api.trip.model.StepResponse
import com.tripian.one.api.trip.model.TripRequest
import com.tripian.one.api.trip.model.TripResponse
import com.tripian.one.api.trip.model.TripsResponse
import com.tripian.one.api.trip.model.UpdatePlanRequest
import com.tripian.one.api.trip.model.UpdateStepRequest
import com.tripian.one.api.trip.model.UpdateStepTimeRequest
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TTrips

internal class TTrips {

    private val service: TTrips by lazy { TNetwork.createService() }

    suspend fun plan(planId: Int): PlanResponse {
        return service.plan(planId)
    }

    suspend fun exportPlan(request: ExportPlanRequest): ExportPlanResponse {
        return service.exportPlan(request)
    }

    suspend fun updatePlan(planId: Int, request: UpdatePlanRequest): PlanResponse {
        return service.updatePlan(planId, request)
    }

    suspend fun trips(dateFrom: String?, dateTo: String?, page: Int?, limit: Int?): TripsResponse {
        return service.trips(dateFrom, dateTo, page, limit)
    }

    suspend fun trip(tripHash: String): TripResponse {
        return service.trip(tripHash)
    }

    suspend fun createTrip(request: TripRequest): TripResponse {
        return service.createTrip(request)
    }

    suspend fun updateTrip(tripHash: String, request: TripRequest): TripResponse {
        return service.updateTrip(tripHash, request)
    }

    suspend fun deleteTrip(tripHash: String): DeleteResponse {
        return service.deleteTrip(tripHash)
    }

    suspend fun stepAlternatives(
        planId: Int, stepId: Int, tripHash: String
    ): StepAlternativesResponse {
        return service.stepAlternatives(planId, stepId, tripHash)
    }

    suspend fun addStep(request: AddStepRequest): StepResponse {
        return service.addStep(request)
    }

    suspend fun addCustomPoiStep(request: AddCustomPoiStepRequest): StepResponse {
        return service.addCustomPoiStep(request)
    }

    suspend fun updateStep(stepId: Int, request: UpdateStepRequest): StepResponse {
        return service.updateStep(stepId, request)
    }

    suspend fun updateStepTime(stepId: Int, request: UpdateStepTimeRequest): StepResponse {
        return service.updateStepTime(stepId, request)
    }

    suspend fun deleteStep(stepId: Int): DeleteResponse {
        return service.deleteStep(stepId)
    }

    suspend fun questions(cityId: Int?, category: String?, languageCode: String?): QuestionsResponse {
        return service.questions(cityId, category, languageCode)
    }
}