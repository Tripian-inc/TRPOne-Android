package com.tripian.one.api.misc.model

import com.tripian.one.network.TNetwork
import com.tripian.one.network.service.TMisc
import okhttp3.ResponseBody

internal class TMisc {
    private val service: TMisc by lazy { TNetwork.createService() }

    suspend fun getLanguages(): ResponseBody {
        return service.getLanguageValues()
    }

    suspend fun getConfigList(): ConfigListResponse {
        return service.getConfigList()
    }

    suspend fun getPoiCategories(): ConfigListResponse {
        return service.getConfigList()
    }
}