package com.tripian.one.api.pois

import com.tripian.one.api.pois.model.PoiCategoriesResponse
import com.tripian.one.api.pois.model.PoiResponse
import com.tripian.one.api.pois.model.PoisResponse
import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TPois

internal class TPois {

    private val service: TPois by lazy { TNetwork.createService() }

    suspend fun getPoiCategories(
        page: Int? = null,
        limit: Int? = 150,
        version: Int? = 2
    ): PoiCategoriesResponse {
        return service.poiCategories(page, limit, version)
    }

    suspend fun getPoi(
        cityId: Int? = null,
        search: String? = null,
        coordinate: Array<out String>? = null,
        poiIds: Array<out String>? = null,
        mustTryIds: Int? = null,
        categoryIds: Array<out Int>? = null,
        distance: Double? = null,
        boundary: String? = null,
        sort: String? = null,
        order: String? = null,
        price: String? = null,
        rating: Array<out Double>? = null,
        page: Int? = null,
        limit: Int? = null,
    ): PoisResponse {
        return service.getPoi(
            cityId,
            search,
            coordinate?.joinToString(","),
            poiIds?.joinToString(","),
            mustTryIds?.toString(),
            categoryIds?.joinToString(","),
            distance,
            boundary,
            sort,
            order,
            price,
            rating?.joinToString(","),
            page,
            limit
        )
    }

    suspend fun getPoiDetail(poiId: String): PoiResponse {
        return service.getPoiDetail(poiId)
    }
}