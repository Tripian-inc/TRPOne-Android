package com.tripian.one

import com.tripian.one.util.TLogger
import com.tripian.one.util.exception.NullResponseException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class TRPRestBase {

    /**
     * Request Job
     */
    val job: Job = Job()
    val scope = CoroutineScope(Dispatchers.Default + job)

    fun <T> sendRequest(
        success: ((T) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null,
        checkToken: Boolean = true,
        service: suspend () -> T,
    ) {
        scope.launch(CoroutineExceptionHandler { _, e ->
            TLogger.log("ExceptionHandler: ${e.message} \nStackTrace: ${e.stackTraceToString()}")

            error?.invoke(e)
        }) {
            val res = runCatching {
                if (checkToken) {
                    TokenManager.checkToken()
                }

                service.invoke()
            }

            if (res.isSuccess) {
                if (res.getOrNull() != null) {
                    withContext(Dispatchers.Main) { success?.invoke(res.getOrNull()!!) }
                } else {
                    withContext(Dispatchers.Main) { error?.invoke(NullResponseException("response returned null")) }
                }
            } else {
                res.exceptionOrNull()?.let { exception ->
                    // 401 veya 403 icin ozel handling
                    if (exception is HttpException && (exception.code() == 401 || exception.code() == 403)) {
                        // 403 Forbidden - token gecersiz, direkt temizle
                        if (exception.code() == 403) {
                            TLogger.log("TRPRestBase - 403 Forbidden, clearing token")
                            TokenManager.clear()
                            withContext(Dispatchers.Main) { error?.invoke(exception) }
                            return@launch
                        }

                        // 401 Unauthorized - refresh token ile retry dene
                        if (TokenManager.token?.refreshToken.isNullOrEmpty()) {
                            TLogger.log("TRPRestBase - 401 received but no refresh token available")
                            withContext(Dispatchers.Main) { error?.invoke(exception) }
                            return@launch
                        }

                        // checkToken kullan (senkronize refresh icin)
                        try {
                            TokenManager.checkToken()
                            // Basarili refresh sonrasi retry
                            sendRequest(success, error, checkToken, service)
                        } catch (e: Exception) {
                            TLogger.log("TRPRestBase - Refresh failed after 401: ${e.message}")
                            withContext(Dispatchers.Main) { error?.invoke(e) }
                        }
                        return@launch
                    }
                    withContext(Dispatchers.Main) { error?.invoke(exception) }
                }
            }
        }
    }
}