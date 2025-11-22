package com.tripian.one.api.favorites.model

import com.tripian.one.api.pois.model.Pagination
import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse

// ------------ request ------------

class FavoriteRequest : BaseRequest() {
    var poiId: String? = null
    var tripHash: String? = null
}

// ------------ response ------------

class FavoritesResponse : BaseResponse() {
    var data: List<Favorite>? = null
    var pagination: Pagination? = null
}

class FavoriteResponse : BaseResponse() {
    var data: Favorite? = null
}