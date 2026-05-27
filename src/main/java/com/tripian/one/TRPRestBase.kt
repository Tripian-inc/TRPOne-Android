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
            var res = runCatching {
                if (checkToken) {
                    TokenManager.checkToken()
                }

                service.invoke()
            }

            // 401 Unauthorized -> force-refresh ve tek seferlik retry.
            // checkToken() lokal saat token'i hala taze sayiyorsa no-op cikar,
            // dolayisiyla server-side invalidation'da refresh tetiklenmezdi;
            // forceRefresh=true bu durumu zorla acar.
            // 403 Forbidden auth problemi degil (validation, rate limit, business
            // rule, ownership vb.) - generic error path'ine dusurulur.
            val firstException = res.exceptionOrNull()
            if (firstException is HttpException && firstException.code() == 401) {
                if (TokenManager.token?.refreshToken.isNullOrEmpty()) {
                    TLogger.log("TRPRestBase - 401 received but no refresh token available")
                    withContext(Dispatchers.Main) { error?.invoke(firstException) }
                    return@launch
                }

                res = runCatching {
                    TokenManager.checkToken(forceRefresh = true)
                    service.invoke()
                }
            }

            if (res.isSuccess) {
                if (res.getOrNull() != null) {
                    withContext(Dispatchers.Main) { success?.invoke(res.getOrNull()!!) }
                } else {
                    withContext(Dispatchers.Main) { error?.invoke(NullResponseException("response returned null")) }
                }
            } else {
                withContext(Dispatchers.Main) { error?.invoke(res.exceptionOrNull()) }
            }
        }
    }
}