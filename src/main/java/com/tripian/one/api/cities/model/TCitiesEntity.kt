package com.tripian.one.api.cities.model

import com.tripian.one.api.pois.model.Coordinate
import com.tripian.one.api.pois.model.Image
import com.tripian.one.api.pois.model.Taste
import java.io.Serializable

class City : Serializable {
    val id: Int = 0
    val parentLocationId: Int? = null
    var name: String? = null
    val boundary: List<Double>? = null
    val mustTries: List<Taste>? = null
    val coordinate: Coordinate? = null
    val country: Country? = null
    val image: Image? = null
    val maxTripDays: Int? = null
}

class Country : Serializable {
    val code: String? = null
    val name: String? = null
    val continent: Continent? = null
}

class Continent : Serializable {
    val name: String? = null
    val slug: String? = null
}