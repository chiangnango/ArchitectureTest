package com.example.myapplication.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.Navigator
import com.example.myapplication.data.APOD

class MainViewModel(private val repository: MainRepository,
                    private val navigator: Navigator) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val _apodList = MutableLiveData<List<APOD>>()
    val apodList: LiveData<List<APOD>> = _apodList
    private val apodListObserver = Observer<List<APOD>> {
        Log.d(TAG, "APODList onChanged() $it")

        _apodList.value = pruneUnknownImage(it)
        _showSpinner.value = false
    }

    private val _showSpinner = MutableLiveData<Boolean>()
    val showSpinner: LiveData<Boolean> = _showSpinner

    init {
        repository.apodList.observeForever(apodListObserver)
    }

    fun fetchAPODList() {
        if (apodList.value == null) {
            fetchAPODListFromRepository()
        }
    }

    fun onAPODItemClicked(apod: APOD) {
        navigator.startAPODDetail(apod)
    }

    fun fetchAPOD(selectedDate: String) {
        val selectedAPOD = apodList.value?.find { it.date == selectedDate }
        if (selectedAPOD == null) {
            fetchAPODListFromRepository()
        }
    }

    private fun fetchAPODListFromRepository() {
        repository.fetchAPODList()
        _showSpinner.value = true
    }

    override fun onCleared() {
        super.onCleared()

        repository.apodList.removeObserver(apodListObserver)
    }

    private fun pruneUnknownImage(list: List<APOD>): List<APOD> {
        return list.filter {
            it.imageUrl.endsWith(".jpg")
        }
    }
}