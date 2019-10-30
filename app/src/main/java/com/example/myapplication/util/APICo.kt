package com.example.myapplication.util

import android.util.Log
import com.example.myapplication.util.APIUtil.API
import com.example.myapplication.util.APIUtil.HttpException
import com.example.myapplication.util.APIUtil.okHttpClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException

abstract class APICo<T> : API<T> {
    companion object {
        private val TAG = APICo::class.java.simpleName
    }

    lateinit var url: String

    suspend fun start(): T {
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)

        return suspendCancellableCoroutine { continuation ->
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (continuation.isCancelled) return

                    Log.d(
                        TAG,
                        "${this@APICo.javaClass.simpleName} onFailure: ${Log.getStackTraceString(e)}"
                    )
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resumeWith(runCatching {
                        val bodyStr = response.body?.string()
                        if (response.isSuccessful) {
                            bodyStr?.let {
                                Log.d(
                                    TAG,
                                    "${this@APICo.javaClass.simpleName} onResponse successful: $it"
                                )
                                parseResult(it)
                            } ?: run {
                                Log.d(
                                    TAG,
                                    "${this@APICo.javaClass.simpleName} onResponse fail: response body is null"
                                )
                                throw NullPointerException("Response body is null")
                            }
                        } else {
                            throw HttpException(response.code, response.message, bodyStr).also {
                                Log.d(
                                    TAG,
                                    "${this@APICo.javaClass.simpleName} onResponse fail: $it"
                                )
                            }
                        }
                    })
                }
            })

            call.registerOnCompletion(continuation)
        }
    }

    private fun Call.registerOnCompletion(continuation: CancellableContinuation<*>) {
        continuation.invokeOnCancellation {
            cancel()
        }
    }
}