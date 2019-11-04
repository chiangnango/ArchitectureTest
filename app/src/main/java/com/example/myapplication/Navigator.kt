package com.example.myapplication

import androidx.fragment.app.Fragment
import com.example.myapplication.data.APOD
import com.example.myapplication.detail.DetailFragment
import com.example.myapplication.util.SingleLiveEvent

object Navigator {

    val navigateFragment = NavigationSingleLiveData<Fragment?>()

    fun startAPODDetail(apod: APOD) {
        navigateFragment.value = DetailFragment.getInstance(apod)
    }

    /**
     * Prevent last fragment started via Navigator leak, clear LiveData after setValue
     */
    class NavigationSingleLiveData<T> : SingleLiveEvent<T>() {
        override fun setValue(t: T?) {
            super.setValue(t)
            super.setValue(null)
        }
    }
}