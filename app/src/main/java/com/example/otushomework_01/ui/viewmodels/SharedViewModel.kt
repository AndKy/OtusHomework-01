package com.example.otushomework_01.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.otushomework_01.data.MovieApplication
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.data.findItem
import com.example.otushomework_01.ui.adapters.FavoritesAdapter
import com.example.otushomework_01.ui.adapters.MoviesAdapter

class SharedViewModel(application: Application)
    : AndroidViewModel(application)
    , MovieApplication.Listener {

    private val _app = application as MovieApplication
    private val _selectedMovie = MutableLiveData<MovieItem>()
    private val _likedMovie = MutableLiveData<MovieItem>()
    private val _detailsMovie = MutableLiveData<MovieItem>()
    private val _connectionError = MutableLiveData<Boolean>(false)
    private var _favAdapter : FavoritesAdapter? = null
    private var _moviesAdapter : MoviesAdapter? = null

    // Scrolling flags used to avoid scrolling on view recreating
    var needScrollToMainPage : Boolean = false
    var needScrollToSelectedMovie : Boolean = false

    val movies : List<MovieItem>
        get() = _app.getMovies()

    val favMovies : List<MovieItem>
        get() = _app.getFavMovies()

    val selectedMovie : LiveData<MovieItem>
        get() = _selectedMovie

    val likedMovie : LiveData<MovieItem>
        get() = _likedMovie

    val detailsMovie : LiveData<MovieItem>
        get() = _detailsMovie

    val connectionError : LiveData<Boolean>
        get() = _connectionError

    init {
        _app.addListener(this)
    }

    fun onFavMoviesAdapterCreated(favAdapter : FavoritesAdapter) {
        _favAdapter = favAdapter

        _favAdapter?.listener = object : FavoritesAdapter.Listener {
            override fun onFavMovieClick(movieItem: MovieItem) {
                needScrollToMainPage = true
                _app.setSelectedMovie(movieItem)
            }
        }
    }

    fun onMovieListAdapterCreated(moviesAdapter : MoviesAdapter) {
        _moviesAdapter = moviesAdapter

        _moviesAdapter?.listener = object : MoviesAdapter.Listener {
            override fun onMovieClick(movieItem: MovieItem) {
                _app.setSelectedMovie(movieItem)
            }

            override fun onMovieFavButtonClick(movieItem: MovieItem) {
                _app.favMovieToggle(movieItem)
                _app.setSelectedMovie(movieItem)

                _likedMovie.value = movieItem
            }

            override fun onMovieDetailsButtonClick(movieItem: MovieItem) {
                _detailsMovie.value = movieItem
            }

            override fun onAddMovieButtonClick() {
                uploadMovies()
            }
        }
    }

    private fun uploadMovies() {
        _connectionError.value = false
        _app.uploadMovies()
    }


    fun onMoviesListScrolledToEnd() {
        uploadMovies()
    }

    fun onMovieSwipeToDelete(movie: MovieItem) {
        _app.removeMovie(movie)
    }

    fun onConnectRetry() {
        uploadMovies()
    }

    fun onCompleteActions() {
        _likedMovie.value = null
        _detailsMovie.value = null
    }

    fun onLikeCancel(movie: MovieItem) {
        _moviesAdapter?.listener?.onMovieFavButtonClick(movie)
    }

    override fun onCleared() {
        _app.removeListener(this)
        super.onCleared()
    }

    override fun onMovieChanged(movie: MovieItem) {
        favMovies.findItem(movie) { i ->
            _favAdapter?.notifyItemChanged(i)
        }
        movies.findItem(movie) { i ->
            _moviesAdapter?.notifyItemChanged(i)
        }
    }

    override fun onMovieAdded(movie: MovieItem, i: Int) {
        _moviesAdapter?.notifyItemInserted(i)
    }

    override fun onMovieRemoved(movie: MovieItem, i: Int) {
        _moviesAdapter?.apply {
            notifyItemRemoved(i)
            notifyItemRangeChanged(i, itemCount - i)
        }
    }

    override fun onFavMovieAdded(movie: MovieItem, i: Int) {
        _favAdapter?.notifyItemInserted(i)
    }

    override fun onFavMovieRemoved(movie: MovieItem, i: Int) {
        _favAdapter?.apply {
            notifyItemRemoved(i)
            notifyItemRangeChanged(i, itemCount - i)
        }
    }

    override fun onMovieSelected(movie: MovieItem) {
        needScrollToSelectedMovie = true
        _selectedMovie.value = movie
    }

    override fun onConnectionError() {
        _connectionError.value = true
    }
}
