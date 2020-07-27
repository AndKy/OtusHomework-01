package com.example.otushomework_01.data

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.otushomework_01.R
import com.example.otushomework_01.ui.fragments.MovieListFragmentEventHandler

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

object Utils {
    fun makeListenerFor(h: MovieListFragmentEventHandler) : MovieApplication.Listener {
        return object : MovieApplication.Listener {
            override fun onMovieChanged(movie: MovieItem) {
                h.onMovieChanged(movie)
            }

            override fun onMovieAdded(movie: MovieItem, pos: Int) {
                h.onMovieAdded(movie, pos)
            }

            override fun onMovieRemoved(movie: MovieItem, pos: Int) {
                h.onMovieRemoved(movie, pos)
            }

            override fun onFavMovieAdded(movie: MovieItem, pos: Int) {
            }

            override fun onFavMovieRemoved(movie: MovieItem, pos: Int) {
            }

            override fun onMovieSelected(movie: MovieItem) {
                h.onMovieSelected(movie)
            }
        }
    }
}