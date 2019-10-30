package com.example.myapplication.main

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.Navigator
import com.example.myapplication.data.APOD
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val navigator: Navigator
) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val _apodList = MutableLiveData<List<APOD>>()
    val apodList: LiveData<List<APOD>> = _apodList
    private val apodListObserver = Observer<Result<List<APOD>>> {
        Log.d(TAG, "APODList onChanged() $it")

        when {
            it.isSuccess -> _apodList.value = pruneUnknownImage(it.getOrDefault(emptyList()))
            it.isFailure -> Log.d(TAG, "fetch APODList fail: ${it.exceptionOrNull()}")
        }
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
        viewModelScope.launch {
            repository.fetchAPODList()
        }
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