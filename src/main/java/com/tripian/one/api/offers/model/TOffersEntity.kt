package com.tripian.one.api.offers.model

import java.io.Serializable

class Offer : Serializable {
    val id: Int = 0
    val tripianPoiId: String = ""
    val businessId: Int = 0
    val title: String = ""
    val description: String? = null
    val productName: String? = null
    val caption: String? = null
    val currency: String? = null
    val threshold: Double? = null
    val discount: Double? = null
    val discountedProductCount: Double? = null
    val usage: Double? = null
    val quantity: Long? = null
    val imageUrl: String? = null
    val productType: ProductTypeModel? = null
    var poiName: String? = null
    var offerType: String? = null // percentage, amount, quantity
    var timeframe: TimeFrameModel? = null
    var optIn: Boolean = false
    var optInDate: String? = null
}

class ProductTypeModel : Serializable {
    val id: Int? = null
    val name: String? = null
    val receiveMethod: String? = null
}

fun ProductTypeModel.isDineIn(): Boolean {
    return (id == OfferType.FOOD_DINE_IN.id || id == OfferType.DRINK_DINE_IN.id)
}

fun ProductTypeModel.isPickUp(): Boolean {
    return (id == OfferType.FOOD_PICK_UP.id || id == OfferType.DRINK_PICK_UP.id || id == OfferType.GROCERY_PICK_UP.id)
}

class TimeFrameModel : Serializable {
    val start: String? = null
    val end: String? = null
}

class OfferDateModel: Serializable {
    val from: String? = null
    val to: String? = null
}

enum class OfferType(val id: Int) {
    FOOD(1),
    FOOD_DINE_IN(1),
    FOOD_PICK_UP(2),
    DRINK(2),
    DRINK_DINE_IN(3),
    DRINK_PICK_UP(4),
    GROCERY(3),
    GROCERY_PICK_UP(5)
}