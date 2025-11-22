package com.tripian.one.api.bookings.model

import com.tripian.one.util.BaseResponse
import java.io.Serializable

// ------------ request ------------

class ReservationRequest : Serializable {
    var poiId = 0
    var key: String? = null
    var provider: String? = null
    var tripHash: String? = null
    var value: ReservationValue? = null
}

// ------------ response ------------

class ReservationsResponse : BaseResponse() {
    var data: List<Reservation>? = null
}

class ReservationResponse : BaseResponse() {
    var data: Reservation? = null
}