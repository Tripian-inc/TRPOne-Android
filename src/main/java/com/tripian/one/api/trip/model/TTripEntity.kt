package com.tripian.one.api.trip.model

import com.google.gson.annotations.SerializedName
import com.tripian.one.api.cities.model.City
import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.Poi
import java.io.Serializable

class Trip : Serializable {
    var id: Int? = null
    var tripHash: String? = null
    var tripProfile: TripProfile? = null
    var city: City? = null
    var plans: ArrayList<Plan>? = null
}

class TripProfile : Serializable {
    var cityId: Int? = null
    var arrivalDatetime: String? = null
    var departureDatetime: String? = null
    var numberOfAdults: Int? = null
    var numberOfChildren: Int? = null
    var owner: String? = null
    var answers: List<Int>? = null
    var accommodation: Accommodation? = null
    var companionIds: List<Int>? = null
    var pace: String? = null
    var doNotGenerate: Int? = null
    var additionalData: String? = null
}

class Accommodation : Serializable {
    var refID: String? = null
    var name: String? = null
    var address: String? = null
    var coordinate: Coordinate? = null
}

class Plan : Serializable {
    val id: Int = 0
    val date: String? = null
    val startTime: String? = null
    val endTime: String? = null
    val generatedStatus: Int? = null
    val statusMessage: String? = null
    val steps: ArrayList<Step>? = null
}

class Step : Serializable {
    val id: Int = 0
    var poi: Poi? = null
    var score: String? = null
    var times: StepHours? = null
    var order: Int? = null
    var alternatives: List<String>? = null
}

class StepHours : Serializable {
    val from: String? = null
    val to: String? = null
}

class Question : Serializable {
    val id = 0

    @SerializedName("skippable")
    val isSkipAble = false

    @SerializedName("selectMultiple")
    val isSelectMultiple = false
    var title: String? = null
    var tmpTitle: String? = null
    var theme: String? = null
    var name: String? = null
    val category: String? = null
    val order: Int? = null

    @SerializedName("answers")
    var answerList: List<Answer>? = null
}

class Answer : Serializable {
    var id: Int? = null
    var name: String? = null
    var description: String? = null

    @SerializedName("subAnswers")
    val subAnswers: List<Answer>? = null

    @Transient
    var imageId = 0
}

fun Trip?.isGenerated(): Boolean {
    if (this == null) {
        return false
    }

    return this.plans != null && !(this.plans!!.any { p -> p.generatedStatus == 0 })
}

fun Plan?.isGenerated(): Boolean {
    if (this == null) {
        return false
    }

    return this.generatedStatus != 0
}

class ExportPlanUrl : Serializable {
    val url: String? = null
}