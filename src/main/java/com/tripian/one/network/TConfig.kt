package com.tripian.one.network

import android.content.Context
import com.tripian.one.api.users.model.Device

object TConfig {
    lateinit var appContext: Context
    lateinit var url: String
    lateinit var key: String
    lateinit var device: Device
    var lang: String = "en"
}