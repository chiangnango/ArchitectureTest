package com.example.myapplication.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageUtil {

    fun load(url: String, target: ImageView, @DrawableRes placeholderResId: Int) {
        Glide.with(target.context)
            .load(url)
            .placeholder(placeholderResId)
            .into(target)
    }

    fun loadRoundedImage(
        url: String,
        target: ImageView,
        radius: Int, @DrawableRes placeholderResId: Int
    ) {
        val option = RequestOptions.bitmapTransform(RoundedCornersTransformation(radius)).apply {
            if (placeholderResId != 0) {
                placeholder(placeholderResId)
            }
        }

        Glide.with(target)
            .load(url)
            .apply(option)
            .into(target)
    }
}