package com.example.myapplication.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.FetchAPODAPI
import com.example.myapplication.data.APOD

class MainCoRepository : MainRepository {

    @VisibleForTesting
    val _apodList = MutableLiveData<Result<List<APOD>>>()
    override val apodList: LiveData<Result<List<APOD>>> = _apodList

    override suspend fun fetchAPODList() {
        try {
            val result = FetchAPODAPI().start()
            _apodList.value = Result.success(result)
        } catch (e: Exception) {
            _apodList.value = Result.failure(e)
        }
    }

    override fun cancel() = Unit
}