package com.tripian.one.util

import android.util.Log

object TLogger {

    var showLog: Boolean = true
    var logListener: Logger? = null

    private val logger = object : Logger {
        override fun log(message: String) {
            if (showLog) {
                Log.v("TService", message)
                logListener?.log(message)
            }
        }
    }

    fun log(message: String) {
        logger.log(message = message)
    }

    fun logEnable(enable: Boolean) {
        showLog = enable
    }
}