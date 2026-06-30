package com.tripian.one.api.cities.model

import com.google.gson.annotations.SerializedName
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
    // IANA timezone id (e.g. "Europe/Madrid") returned by the cities service.
    // Gson maps this automatically from the JSON "timezone" key.
    val timezone: String? = null
    // Whether the city is featured as a "popular" destination by the cities
    // service (JSON key "isPopular"). Defaults to false so older cached JSON
    // without the key deserializes safely.
    @SerializedName("isPopular")
    val isPopular: Boolean = false
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