package com.example.otushomework_01.data

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.otushomework_01.ui.fragments.FavoritesFragmentEventHandler
import com.example.otushomework_01.ui.fragments.MovieListFragmentEventHandler

interface Destroyable {
    fun onDestroy()
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

    fun makeListenerFor(h: FavoritesFragmentEventHandler) : MovieApplication.Listener {
        return object : MovieApplication.Listener {
            override fun onMovieChanged(movie: MovieItem) {
                h.onMovieChanged(movie)
            }

            override fun onMovieAdded(movie: MovieItem, pos: Int) {
            }

            override fun onMovieRemoved(movie: MovieItem, pos: Int) {
            }

            override fun onFavMovieAdded(movie: MovieItem, pos: Int) {
                h.onMovieAdded(movie, pos)
            }

            override fun onFavMovieRemoved(movie: MovieItem, pos: Int) {
                h.onMovieRemoved(movie, pos)
            }

            override fun onMovieSelected(movie: MovieItem) {
                h.onMovieSelected(movie)
            }
        }
    }
}