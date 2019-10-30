package com.example.myapplication.api

import com.example.myapplication.data.APOD
import com.example.myapplication.util.APICallback
import com.example.myapplication.util.APIUtil.URL
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class FetchAPODAPICallback : APICallback<List<APOD>>() {

    companion object {
        @Throws(JsonSyntaxException::class)
        fun parseAPODList(response: String): List<APOD> {
            val collectionType = object : TypeToken<List<APOD>>() {}.type
            return Gson().fromJson(response, collectionType)
        }
    }

    init {
        url = "${URL}&start_date=2019-09-01&end_date=2019-10-08"
    }

    @Throws(JsonSyntaxException::class)
    override fun parseResult(response: String): List<APOD> = parseAPODList(response)
}