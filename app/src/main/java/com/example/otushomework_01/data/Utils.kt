package com.example.otushomework_01.data

import com.example.otushomework_01.ui.fragments.FavoritesFragmentEventHandler
import com.example.otushomework_01.ui.fragments.MovieListFragmentEventHandler

interface Destroyable {
    fun onDestroy()
}

object Utils {
    fun makeListenerFor(h: MovieListFragmentEventHandler) : Application.Listener {
        return object : Application.Listener {
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

    fun makeListenerFor(h: FavoritesFragmentEventHandler) : Application.Listener {
        return object : Application.Listener {
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