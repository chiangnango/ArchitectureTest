package com.example.myapplication.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.FetchAPODAPICallback
import com.example.myapplication.data.APOD
import com.example.myapplication.util.APICallback

class MainRepository {

    private var api: FetchAPODAPICallback? = null

    @VisibleForTesting
    val _apodList = MutableLiveData<List<APOD>>()
    val apodList: LiveData<List<APOD>> = _apodList

    fun fetchAPODList() {
        cancelFetchAPOD()
        api = FetchAPODAPICallback().apply {
            callback = object : APICallback.Callback<List<APOD>> {
                override fun onError(e: Exception) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(httpStatusCode: Int, response: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(result: List<APOD>) {
                    _apodList.value = result
                }
            }
            start()
        }
    }

    fun cancelFetchAPOD() {
        api?.cancel()
    }
}