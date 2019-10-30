package com.example.myapplication.architecture

import com.example.myapplication.Navigator
import com.example.myapplication.main.MainCoRepository
import com.example.myapplication.main.MainViewModelFactory

object InjectorUtil {

    fun provideMainViewModelFactory(navigator: Navigator): MainViewModelFactory {
        return MainViewModelFactory(MainCoRepository(), navigator)
    }
}