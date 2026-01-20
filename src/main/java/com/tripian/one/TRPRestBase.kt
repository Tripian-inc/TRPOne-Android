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
                withContext(Dispatchers.Main) {
                    res.exceptionOrNull()?.let { exception ->
                        // Only handle 401 for HttpException, otherwise pass through
                        if (exception is HttpException && exception.code() == 401) {
                            val resToken = TokenManager.refreshToken()
                            if (resToken.isSuccess) {
                                TokenManager.token = resToken.getOrNull()?.data
                                sendRequest(success, error, checkToken, service)
                            } else {
                                error?.invoke(resToken.exceptionOrNull())
                            }
                            return@withContext
                        }
                        error?.invoke(exception)
                    }
                }
            }
        }
    }
}