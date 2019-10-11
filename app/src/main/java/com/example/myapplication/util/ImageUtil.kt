package com.example.myapplication.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtil {

    fun load(url: String, target: ImageView) {
        Glide.with(target.context)
            .load(url)
            .placeholder(android.R.color.darker_gray)
            .into(target)
    }
}