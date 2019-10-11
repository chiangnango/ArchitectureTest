package com.example.myapplication.api

import com.example.myapplication.data.APOD
import com.example.myapplication.util.API
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken


class FetchAPODAPI : API<List<APOD>>() {

    init {
        url = "${URL}&start_date=2019-09-01&end_date=2019-10-08"
    }

    @Throws(JsonSyntaxException::class)
    override fun parseResult(response: String): List<APOD> {
        val collectionType = object : TypeToken<List<APOD>>() {}.type
        return Gson().fromJson(response, collectionType)
    }
}