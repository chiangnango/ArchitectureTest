package com.example.myapplication.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.FetchAPODAPICallback
import com.example.myapplication.data.APOD
import com.example.myapplication.util.APICallback
import com.example.myapplication.util.APIUtil.HttpException

class MainCallbackRepository : MainRepository {

    @VisibleForTesting
    val _apodList = MutableLiveData<Result<List<APOD>>>()
    override val apodList: LiveData<Result<List<APOD>>> = _apodList

    private var api: FetchAPODAPICallback? = null

    override suspend fun fetchAPODList() {
        cancel()
        api = FetchAPODAPICallback().apply {
            callback = object : APICallback.Callback<List<APOD>> {
                override fun onError(e: Exception) {
                    _apodList.value = Result.failure(e)
                }

                override fun onFailure(httpStatusCode: Int, message: String, response: String) {
                    _apodList.value =
                        Result.failure(HttpException(httpStatusCode, message, response))
                }

                override fun onResponse(result: List<APOD>) {
                    _apodList.value = Result.success(result)
                }
            }
            start()
        }
    }

    override fun cancel() {
        api?.cancel()
    }
}