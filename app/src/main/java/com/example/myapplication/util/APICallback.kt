package com.example.myapplication.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplication.util.APIUtil.API
import com.example.myapplication.util.APIUtil.okHttpClient
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

abstract class APICallback<T> : API<T> {
    companion object {
        private val TAG = Callback::class.java.simpleName
    }

    lateinit var url: String

    private var call: Call? = null

    private val uiHandler = Handler(Looper.getMainLooper())

    var callback: Callback<T>? = null

    private val okhttpCallback = object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d(
                TAG,
                "${this@APICallback.javaClass.simpleName} error: ${Log.getStackTraceString(e)}"
            )
            callback?.onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            val responseStr = response.body?.string() ?: ""
            if (response.isSuccessful) {
                Log.d(TAG, "${this@APICallback.javaClass.simpleName} successful: $responseStr")
                try {
                    val result = parseResult(responseStr)
                    result?.let {
                        uiHandler.post {
                            callback?.onResponse(it)
                        }
                    }
                } catch (e: Exception) {
                    uiHandler.post {
                        callback?.onError(e)
                    }
                }
            } else {
                Log.d(TAG, "${this@APICallback.javaClass.simpleName} failure: $responseStr")
                uiHandler.post {
                    callback?.onFailure(response.code, responseStr)
                }
            }
        }
    }

    fun start() {
        val request = Request.Builder().url(url).build()
        call = okHttpClient.newCall(request).apply {
            enqueue(okhttpCallback)
        }
    }

    fun cancel() {
        callback = null
        call?.cancel()
    }

    interface Callback<T> {
        fun onError(e: Exception)
        fun onFailure(httpStatusCode: Int, response: String)
        fun onResponse(result: T)
    }
}