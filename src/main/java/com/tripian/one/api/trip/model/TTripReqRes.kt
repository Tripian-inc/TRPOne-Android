package com.tripian.one.api.trip.model

import com.google.gson.annotations.SerializedName
import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.ImageOwner
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse
import java.io.Serializable

// ------------ request ------------
class UpdatePlanRequest : BaseRequest() {
    var startTime: String? = null
    var endTime: String? = null
    var stepOrders: List<Int>? = null
}

data class CustomPoiRequest(
    var name: String? = null,
    var address: String? = null,
    var description: String? = null,
    var photos: List<CustomPoiPhotoModel>? = null,
    var web: String? = null,
    var coordinate: Coordinate? = null
) : BaseRequest()

data class CustomPoiPhotoModel(
    var url: String,
    var copyright: ImageOwner? = null,
    @SerializedName("is_featured")
    var isFeatured: Boolean = false
) : BaseRequest()

data class TripRequest(
    var cityId: Int,
    var arrivalDatetime: String,
    var departureDatetime: String,
    var numberOfAdults: Int,
    var numberOfChildren: Int? = null,
    var accommodation: Accommodation? = null,
    var answers: List<Int>? = null,
    var companionIds: List<Int>? = null,
    var theme: String? = null,
    var additionalData: String? = null,
    var doNotGenerate: Int? = null
) : BaseRequest()

data class AddStepRequest(var planId: Int, var poiId: String) : BaseRequest()

data class AddCustomPoiStepRequest(
    var planId: Int,
    var customPoi: CustomPoiRequest,
    var stepType: String
) : BaseRequest()

data class UpdateStepRequest(var order: Int?, var poiId: String?) : BaseRequest()

data class UpdateStepTimeRequest(var startTime: String?, var endTime: String?) : BaseRequest()

// ------------ response ------------

class PlanResponse : BaseResponse() {
    val data: Plan? = null
}

data class ExportPlanRequest(var planId: Int, var tripHash: String) : BaseRequest()
class ExportPlanResponse : BaseResponse() {
    val data: ExportPlanUrl? = null
}

class TripsResponse : BaseResponse() {
    val data: List<Trip>? = null
    val pagination: Trip? = null
}

class TripResponse : BaseResponse() {
    var data: Trip? = null
    val pagination: Trip? = null
}

class DeleteResponse : BaseResponse() {
    val data: DeletedModel? = null
}

class DeletedModel : Serializable {
    var recordId: Int? = null
}

class StepResponse : BaseResponse() {
    var data: Step? = null
}

class StepAlternativesResponse : BaseResponse() {
    var data: List<Int>? = null
}

class QuestionsResponse : BaseResponse() {
    var data: List<Question>? = null
}