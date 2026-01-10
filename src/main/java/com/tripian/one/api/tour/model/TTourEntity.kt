package com.tripian.one.api.tour.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Tour Product model representing a tour/activity
 */
class TourProduct : Serializable {
    @SerializedName("product_id")
    var productId: String = ""

    @SerializedName("provider_id")
    var providerId: Int = 0

    var id: String = ""

    @SerializedName("city_id")
    var cityId: Int = 0

    var title: String = ""

    var description: String? = null

    var url: String? = null

    var price: Double? = null

    var currency: String? = null

    @SerializedName("current_price")
    var currentPrice: Double? = null

    var duration: Double? = null

    var rating: Double? = null

    @SerializedName("rating_count")
    var ratingCount: Int? = null

    var status: Int? = null

    var available: Boolean? = null

    var version: String? = null

    var images: List<TourImage>? = null

    @SerializedName("location_names")
    var locationNames: List<String>? = null

    @SerializedName("tag_ids")
    var tagIds: List<Int>? = null

    var tags: List<String>? = null

    var locations: List<TourLocation>? = null

    @SerializedName("tripian_pois")
    var tripianPois: List<String>? = null
}

/**
 * Tour Image model
 */
class TourImage : Serializable {
    @SerializedName("is_cover")
    var isCover: Boolean? = null

    var url: String? = null
}

/**
 * Tour Location model
 * Note: API uses "lon" not "lng"
 */
class TourLocation : Serializable {
    var lat: Double? = null
    var lon: Double? = null  // API uses "lon" not "lng"
}

/**
 * Tour Schedule model
 */
class TourSchedule : Serializable {
    var productId: String? = null

    var title: String? = null

    var date: String? = null

    var tags: List<String>? = null

    var duration: Double? = null

    var slots: List<TourScheduleSlot>? = null
}

/**
 * Tour Schedule Slot model
 */
class TourScheduleSlot : Serializable {
    var time: String? = null

    var price: Double? = null

    var fullRefund: Boolean? = null
}

/**
 * Tour Search Data wrapper
 */
class TourSearchData : Serializable {
    var products: List<TourProduct>? = null

    var total: Int? = null

    var limit: Int? = null

    var offset: Int? = null
}
