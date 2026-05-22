package com.tripian.one.network.service

import com.tripian.one.api.misc.model.ConfigListResponse
import com.tripian.one.api.misc.model.LogRequest
import com.tripian.one.api.misc.model.LogResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface TMisc {

    @GET("misc/frontend-translations")
    suspend fun getLanguageValues(): ResponseBody

    @GET("misc/config-list")
    suspend fun getConfigList(): ConfigListResponse

    @POST("misc/logs")
    suspend fun sendLog(@Body request: LogRequest): LogResponse
}