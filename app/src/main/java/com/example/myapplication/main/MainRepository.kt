package com.example.myapplication.main

import androidx.lifecycle.LiveData
import com.example.myapplication.data.APOD

interface MainRepository {
    val apodList: LiveData<Result<List<APOD>>>

    suspend fun fetchAPODList()
    fun cancel()
}