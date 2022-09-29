package com.towertex.tmdb.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("glideUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) return
    Glide
        .with(view)
        .load(url)
        .into(view)
}

@BindingAdapter("glideUrl")
fun loadImage(view: ImageView, url: StateFlow<String?>) {
    if (url.value.isNullOrEmpty()) return
    Glide
        .with(view)
        .load(url)
        .into(view)
}