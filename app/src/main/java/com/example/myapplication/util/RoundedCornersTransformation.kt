package com.example.myapplication.util

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest


class RoundedCornersTransformation(
    private val radius: Int,
    private val squareTL: Boolean = false,
    private val squareTR: Boolean = false,
    private val squareBL: Boolean = false,
    private val squareBR: Boolean = false
) : BitmapTransformation() {

    private val ID = RoundedCornersTransformation::class.java.name
    private val ID_BYTES = ID.toByteArray()

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat())
            , radius.toFloat()
            , radius.toFloat()
            , paint
        )
        drawSquareCorner(canvas, paint, width.toFloat(), height.toFloat())
        return bitmap
    }

    private fun drawSquareCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        if (squareTL) {
            canvas.drawRect(0f, 0f, width / 2, height / 2, paint)
        }
        if (squareTR) {
            canvas.drawRect(width / 2, 0f, width, height / 2, paint)
        }
        if (squareBL) {
            canvas.drawRect(0f, height / 2, width / 2, height, paint)
        }
        if (squareBR) {
            canvas.drawRect(width / 2, height / 2, width, height, paint)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RoundedCornersTransformation
    }

    override fun toString(): String {
        return ("RoundedTransformation(radius= $radius")
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}