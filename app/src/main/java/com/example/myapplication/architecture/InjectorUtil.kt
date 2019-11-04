package com.example.myapplication.architecture

import com.example.myapplication.main.MainCoRepository
import com.example.myapplication.main.MainViewModelFactory

object InjectorUtil {

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(MainCoRepository())
    }
}