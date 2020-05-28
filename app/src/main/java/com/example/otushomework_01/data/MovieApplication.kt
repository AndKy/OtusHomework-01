package com.example.otushomework_01.data

import android.app.Application
import android.content.Context
import com.example.otushomework_01.R

class MovieApplication : Application() {

    interface Listener {
        fun onMovieChanged(movie: MovieItem)
        fun onMovieAdded(movie: MovieItem, pos: Int)
        fun onMovieRemoved(movie: MovieItem, pos: Int)
        fun onFavMovieAdded(movie: MovieItem, pos: Int)
        fun onFavMovieRemoved(movie: MovieItem, pos: Int)
        fun onMovieSelected(movie: MovieItem)
    }

    private val movies = ArrayList<MovieItem>()
    private val favorites = ArrayList<MovieItem>()
    private var selectedMovie: MovieItem? = null
    private val listeners = ArrayList<Listener>()

    override fun onCreate() {
        super.onCreate()
        initMovieList()
    }

    private fun initMovieList() {
        arrayOf(
            MovieItem(
                R.drawable.movie_1_little,
                R.drawable.movie_1_big,
                getString(R.string.movie_1_title),
                getString(R.string.movie_1_desc),
                getString(R.string.movie_1_about)
            ),
            MovieItem(
                R.drawable.movie_2_little,
                R.drawable.movie_2_big,
                getString(R.string.movie_2_title),
                getString(R.string.movie_2_desc),
                getString(R.string.movie_2_about)
            ),
            MovieItem(
                R.drawable.movie_3_little,
                R.drawable.movie_3_big,
                getString(R.string.movie_3_title),
                getString(R.string.movie_3_desc),
                getString(R.string.movie_3_about)
            )
        ).forEach { Application.addMovie(it) }

        // Add number of random movies
        repeat(9) {
            Application.addNewMovie()
        }
    }

    fun getMovies(): List<MovieItem> =
        movies
    fun getFavMovies(): List<MovieItem> =
        favorites
    fun addListener(listener: Listener) = listeners.add(listener)
    fun removeListener(listener: Listener) = listeners.remove(listener)

    fun addMovie(movie: MovieItem) {
        movies.add(movie)
        listeners.forEach { it.onMovieAdded(movie, movies.size - 1) }
    }

    fun setSelectedMovie(movie: MovieItem?) {
        if (selectedMovie != movie) {
            selectedMovie?.let { selMovie: MovieItem ->
                selMovie.isSelected = false
                listeners.forEach { it.onMovieChanged(selMovie) }
            }

            selectedMovie = movie
            movie?.let { selMovie: MovieItem ->
                selMovie.isSelected = true
                listeners.forEach { it.onMovieSelected(selMovie) }
                listeners.forEach { it.onMovieChanged(selMovie) }
            }
        }
    }

    fun addNewMovie() {
        val movie =
            Hollywood.makeNewMovie(movies)
        addMovie(movie)
    }

    fun removeMovie(movie: MovieItem) {
        var pos = movies.indexOfFirst { it === movie }
        if (pos in movies.indices) {
            movies.removeAt(pos)
            listeners.forEach { it.onMovieRemoved(movie, pos) }

            pos = favorites.indexOfFirst { it === movie }
            if (pos in favorites.indices) {
                favorites.removeAt(pos)
                listeners.forEach { it.onFavMovieRemoved(movie, pos) }
            }
        }
    }

    fun favMovieToggle(movie: MovieItem) {
        if (movie.isFavorite)
            favMovieRemove(movie)
        else
            favMovieAdd(movie)
    }

    private fun favMovieAdd(movie: MovieItem) {
        movie.isFavorite = true

        val pos = favorites.indexOfFirst { it === movie }
        if (pos == -1) {
            favorites.add(movie)
            listeners.forEach { it.onFavMovieAdded(movie, favorites.size - 1) }
        }

        listeners.forEach { it.onMovieChanged(movie) }
    }

    private fun favMovieRemove(movie: MovieItem) {
        movie.isFavorite = false

        val pos = favorites.indexOfFirst { it === movie }
        if (pos in favorites.indices) {
            favorites.removeAt(pos)
            listeners.forEach { it.onFavMovieRemoved(movie, pos) }
        }

        listeners.forEach { it.onMovieChanged(movie) }
    }
}

val Context.Application: MovieApplication
    get() = applicationContext as MovieApplication