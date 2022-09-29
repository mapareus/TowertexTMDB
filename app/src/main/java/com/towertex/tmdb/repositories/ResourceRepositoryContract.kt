package com.towertex.tmdb.repositories

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable

interface ResourceRepositoryContract {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg values: Any): String
    fun getDimensionPixelSize(resId: Int): Int
    fun getInteger(resId: Int): Int
    fun getColor(resId: Int): Int
    fun getDrawable(resId: Int): Drawable?
    fun getPixelSizeFromDimens(dimId: Int): Float
    fun getFloat(resId: Int): Float
    fun getColorStateList(resId: Int): ColorStateList
    fun getGlideUrl(url: String?): String?
}