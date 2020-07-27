package com.example.otushomework_01.data

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.otushomework_01.R

/* Extension function: find position of 'item' in list and invoke 'callback' with position found
* */
fun <T> List<T>.findItem(item: T, callback: (i: Int) -> Unit ) {
    val i = indexOfFirst { it === item }
    if (i in indices)
        callback(i)
}

fun ImageView.assign(url: String) {
    val drawableId = url.toIntOrNull()

    if (drawableId != null) {
        setImageResource(drawableId)
    }
    else {
        Glide.with(this)
            .load(url)
            .transform(CenterCrop())
            .error(R.drawable.ic_visibility_off_black_24dp)
            .into(this)
    }
}