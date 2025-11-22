package com.tripian.one.api.bookings.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Reservation : Serializable {
    var id: Int? = null
    var poiId: String = ""
    var key: String? = null
    var provider: String? = null
    var tripHash: String? = null
    var value: ReservationValue? = null
}

class ReservationValue : Serializable {
    var data: ReservationData? = null
}

class ReservationData : Serializable {
    @SerializedName("shopping_cart")
    var shoppingCart: ShoppingCart? = null
}

class ShoppingCart : Serializable {
    @SerializedName("tour_name")
    var tourName: String? = null
    @SerializedName("city_name")
    var cityName: String? = null
    @SerializedName("tour_image")
    var tourImage: String? = null
    @SerializedName("shopping_cart_id")
    var shoppingCartId: Long? = null
    @SerializedName("shopping_cart_hash")
    var shoppingCartHash: String? = null

    @SerializedName("booking_hash")
    var bookingHash: String? = null

    var status: String? = null

    var traveler: Traveler? = null
    var billing: Billing? = null
    @SerializedName("payment_info")
    var paymentInfo: PayInfo? = null
    var bookings: List<PayBooking>? = null
}

class Billing : Serializable {
    @SerializedName("country_code")
    var countryCode: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null
    var email: String? = null

    val is_company = false
    val company_name = "Tripian"
    val invoice = false
    var address_line_1: String? = null
    var address_line_2: String? = null
    var city: String? = null
    var postal_code: String? = null
    var state: String? = null
    val salutation_code = "m"
}

class Traveler : Serializable {
    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null
    var email: String? = null

    val salutation_code = "m"
}

open class PayInfo : Serializable {
    @SerializedName("payment_currency")
    var currency: String? = null
    @SerializedName("total_price")
    var totalPrice: Double? = null
    @SerializedName("precoupon_price")
    var precouponPrice: Double? = null
    @SerializedName("payment_method")
    var paymentMethod: String? = null
    @SerializedName("coupon_info")
    var couponInfo: String? = null
    @SerializedName("invoice_reference")
    var invoiceReference: String? = null
}

open class PayBooking : Serializable {
    @SerializedName("booking_id")
    var bookingId: Long? = null
    @SerializedName("booking_hash")
    var bookingHash: String? = null
    @SerializedName("booking_status")
    var bookingStatus: String? = null
    @SerializedName("shopping_cart_id")
    var shoppingCartId: Long? = null
    @SerializedName("shopping_cart_hash")
    var shoppingCartHash: String? = null
    @SerializedName("bookable")
    var bookable: Bookable? = null
    @SerializedName("ticket")
    var ticket: PayTicket? = null
}

open class PayTicket : Serializable {
    @SerializedName("booking_reference")
    var bookingReference: String? = null
    @SerializedName("voucher_information")
    var voucherInformation: String? = null
    @SerializedName("emergency_phone_number")
    var emergencyPhoneNumber: String? = null
    @SerializedName("ticket_hash")
    var ticketHash: String? = null
    @SerializedName("emergency_email")
    var emergencyEmail: String? = null
    @SerializedName("ticket_url")
    var ticketUrl: String? = null
}

class Bookable : Serializable {
    @SerializedName("tour_id")
    var tourId: Long? = null
    @SerializedName("option_id")
    var optionId: Long? = null
    @SerializedName("datetime_type")
    var dateTimeType: String? = null
    @SerializedName("datetime")
    var dateTime: String? = null
    @SerializedName("valid_until")
    var validUntil: String? = null

    @SerializedName("cancellation_policy_text")
    var cancellableText: String? = null
    @SerializedName("currently_cancellable_at_no_fee")
    var cancellable: Boolean? = null

    @SerializedName("booking_parameters")
    var parameters: ArrayList<BookingParameters>? = null
    var price: Double = 0.0

    @Transient
    var optionTitle: String? = null

    var categories: List<BookingCategory>? = null
}

class BookingParameters : Serializable {
    var name: String? = null

    @SerializedName("value_1")
    var value1: String? = null

    @SerializedName("value_2")
    var value2: String? = null

    var description: String? = null
    var mandatory: Boolean? = null
}

class BookingCategory : Serializable {
    @SerializedName("number_of_participants")
    var participants: Int? = null

    @SerializedName("category_id")
    var categoryId: Long? = null

    @Transient
    var categoryName: String? = null
}