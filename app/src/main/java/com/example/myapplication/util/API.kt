package com.example.myapplication.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.WorkerThread
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

abstract class API<Result> {
    companion object {
        private val TAG = API::class.java.simpleName

        const val URL = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    lateinit var url: String

    private var call: Call? = null

    private val uiHandler = Handler(Looper.getMainLooper())

    var apiCallback: APICallback<Result>? = null

    private val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d(TAG, "${this.javaClass.simpleName} error: ${Log.getStackTraceString(e)}")
            apiCallback?.onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            val responseStr = response.body?.string() ?: ""
            if (response.isSuccessful) {
                Log.d(TAG, "${this.javaClass.simpleName} successful: $responseStr")
                try {
                    val result = parseResult(responseStr)
                    result?.let {
                        uiHandler.post {
                            apiCallback?.onResponse(it)
                        }
                    }
                } catch (e: Exception) {
                    uiHandler.post {
                        apiCallback?.onError(e)
                    }
                }
            } else {
                Log.d(TAG, "${this.javaClass.simpleName} failure: $responseStr")
                uiHandler.post {
                    apiCallback?.onFailure(response.code, responseStr)
                }
            }
        }
    }

    fun start() {
        val request = Request.Builder().url(url).build()
        call = okHttpClient.newCall(request).apply {
            enqueue(callback)
        }
    }

    fun cancel() {
        apiCallback = null
        call?.cancel()
    }

    @WorkerThread
    abstract fun parseResult(response: String): Result

    interface APICallback<Result> {
        fun onError(e: Exception)
        fun onFailure(httpStatusCode: Int, response: String)
        fun onResponse(result: Result)
    }
}