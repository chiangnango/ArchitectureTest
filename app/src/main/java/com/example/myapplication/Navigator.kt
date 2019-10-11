package com.example.myapplication

import androidx.fragment.app.Fragment
import com.example.myapplication.data.APOD
import com.example.myapplication.detail.DetailFragment
import com.example.myapplication.util.SingleLiveEvent

class Navigator {

    val navigateFragment = SingleLiveEvent<Fragment>()

    fun startAPODDetail(apod: APOD) {
        navigateFragment.value = DetailFragment.getInstance(apod)
    }
}