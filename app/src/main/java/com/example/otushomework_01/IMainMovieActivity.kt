package com.example.otushomework_01

interface IMainMovieActivity {
    fun getMovies() : ArrayList<MovieItem>
    fun getFavorites() : ArrayList<MovieItem>
    fun onFavoriteMovieClick(movie: MovieItem)
    fun onMovieSelected(movie: MovieItem)
    fun onMovieUnselected(movie: MovieItem)
    fun onToggleFavoriteMovie(movie: MovieItem)
}