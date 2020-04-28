package com.example.otushomework_01

interface IMainMovieActivity {
    open fun getMovies() : ArrayList<MovieItem>
    open fun getFavorites() : ArrayList<MovieItem>
    open fun onFavoriteMovieClick(movie: MovieItem)
    open fun onMovieSelected(movie: MovieItem)
    open fun onMovieUnselected(movie: MovieItem)
}