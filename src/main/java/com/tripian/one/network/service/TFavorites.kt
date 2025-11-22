package com.tripian.one.network.service

import com.tripian.one.api.favorites.model.FavoriteRequest
import com.tripian.one.api.favorites.model.FavoriteResponse
import com.tripian.one.api.favorites.model.FavoritesResponse
import com.tripian.one.api.trip.model.DeleteResponse
import retrofit2.http.*

internal interface TFavorites {

    @POST("favorites")
    suspend fun addFavorite(@Body request: FavoriteRequest): FavoriteResponse

    @GET("favorites")
    suspend fun favorites(
        @Query("cityId") cityId: Int?, @Query("tripHash") tripHash: String?,
        @Query("boundary") boundary: String?, @Query("page") page: Int?, @Query("limit") limit: Int?
    ): FavoritesResponse

    @DELETE("favorites/{favoriteId}")
    suspend fun deleteFavorite(@Path("favoriteId") favoriteId: Int): DeleteResponse
}