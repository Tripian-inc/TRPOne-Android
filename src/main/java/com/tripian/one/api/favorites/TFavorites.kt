package com.tripian.one.api.favorites

import com.tripian.one.api.favorites.model.FavoriteRequest
import com.tripian.one.api.favorites.model.FavoriteResponse
import com.tripian.one.api.favorites.model.FavoritesResponse
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TFavorites

internal class TFavorites {

    private val service: TFavorites by lazy { TNetwork.createService() }

    suspend fun addFavorite(request: FavoriteRequest): FavoriteResponse {
        return service.addFavorite(request)
    }

    suspend fun favorites(cityId: Int?, tripHash: String?, boundary: String?, page: Int?, limit: Int?): FavoritesResponse {
        return service.favorites(cityId, tripHash, boundary, page, limit)
    }

    suspend fun deleteFavorite(favoriteId: Int): DeleteResponse {
        return service.deleteFavorite(favoriteId)
    }
}