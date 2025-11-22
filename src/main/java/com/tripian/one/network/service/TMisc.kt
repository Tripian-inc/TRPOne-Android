package com.tripian.one.network.service

import com.tripian.one.api.misc.model.ConfigListResponse
import okhttp3.ResponseBody
import retrofit2.http.GET

internal interface TMisc {

    @GET("misc/frontend-translations")

    suspend fun getLanguageValues(): ResponseBody
    @GET("misc/config-list")
    suspend fun getConfigList(): ConfigListResponse
}