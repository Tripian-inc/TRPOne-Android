package com.tripian.one.api.pois.model

import com.tripian.one.api.offers.model.Offer
import java.io.Serializable

class Poi : Serializable {
    var id: String = ""
    var cityId: Int? = null
    var name: String? = null
    var price: Int? = null
    var duration: Int? = null
    var rating = 0f
    var ratingCount: Int? = null
    var description: String? = null
    var web: String? = null
    var phone: String? = null
    var address: String? = null
    var icon: String? = null
    var coordinate: Coordinate? = null
    var category: List<PoiCategory>? = null
    var cuisines: String? = null
    var hours: String? = null
    var closed: List<Int>? = null
    var distance = 0f
    var status = false
    var attention: String? = null
    var tags: List<String>? = null
    var markerCoordinate: Coordinate? = null
    var gallery: List<Image>? = null
    var image: Image? = null
    var additionalData: AdditionalData? = null
    val bookings: List<Booking>? = null
    val mustTries: List<Taste>? = null
    val offers: List<Offer>? = null
}

class Coordinate : Serializable {
    var lat = 0.0
    var lng = 0.0
}

class AdditionalData : Serializable {
    var bookingUrl: String? = null
}

class PoiCategoryModel : Serializable {
    val categories: List<PoiCategory>? = null
    val groups: List<PoiCategoryGroup>? = null
}

class PoiCategoryGroup : Serializable {
    val name: String? = null
    val categories: List<PoiCategory>? = null
}

class PoiCategory : Serializable {
    val id: Int = 0
    val name: String? = null
    val isCustom: Boolean? = null
}

class Image : Serializable {
    val url: String? = null
    val width: Int? = null
    val height: Int? = null
    val imageOwner: ImageOwner? = null
}

class ImageOwner : Serializable {
    val title: String? = null
    val url: String? = null
    val avatar: String? = null
}

class Taste : Serializable {
    val id: Int = 0
    val name: String? = null
    val description: String? = null
    val image: Image? = null
}

class Booking : Serializable {
    val providerId: Int? = null
    val providerName: String? = null
    val products: List<Product>? = null
}

class Product : Serializable {
    val id: String? = null
    val title: String? = null
    val rating: Float? = null
    val ratingCount: Int? = null
    val price: Float? = null
    private val info: List<String>? = null
    val url: String? = null
    val image: String? = null
}

class Pagination {
    val count: Int? = null
    val total: Int? = null
    val perPage: Int? = null
    val currentPage: Int? = null
    val totalPages: Int? = null
}