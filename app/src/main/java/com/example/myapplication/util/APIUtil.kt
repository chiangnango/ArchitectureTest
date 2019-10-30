package com.example.myapplication.util

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object APIUtil {
    const val URL = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    interface API<T> {
        fun parseResult(response: String): T
    }

    data class HttpException(
        val code: Int,
        val msg: String,
        val body: String?
    ) : Exception("HTTP $code $msg")
}