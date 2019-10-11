package com.example.myapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Navigator

class MainViewModelFactory(private val repository: MainRepository,
                           private val navigator: Navigator): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, navigator) as T
    }
}