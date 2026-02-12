package com.tripian.one.api.pois.model

import java.io.Serializable

/**
 * POI Location Info Model
 * Contains geographic location information for a POI
 */
class PoiLocationInfo : Serializable {
    var continent: String? = null
    var country: String? = null
    var locationType: String? = null
    var name: String? = null
    var id: Int? = null
}
