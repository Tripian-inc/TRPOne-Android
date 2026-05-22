package com.tripian.one.api.misc.model

import com.tripian.one.util.BaseRequest
import com.tripian.one.util.BaseResponse
import java.io.Serializable

/**
 * LogRequest - Request model for misc/logs endpoint
 * Used to send itinerary parameters and other SDK logs to backend
 */
class LogRequest : BaseRequest() {
    var message: LogMessage? = null
}

/**
 * LogMessage - The message payload for logging
 */
class LogMessage : Serializable {
    var platform: String = "android"
    var type: String = "INFO"
    var g_api_customer_id: Int = 0
    var user_id: Int = 0
    var uri_params: Map<String, Any?>? = null
    var query_params: Map<String, Any?>? = null
    var api_key: String? = null
    var endpoint: String? = null
    var response_msg: String? = null
    var request_params: Map<String, Any?>? = null
}

/**
 * LogResponse - Response model for misc/logs endpoint
 */
class LogResponse : BaseResponse()
