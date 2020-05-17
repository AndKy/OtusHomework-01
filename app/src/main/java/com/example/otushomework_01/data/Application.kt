package com.example.otushomework_01.data

object Application {

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