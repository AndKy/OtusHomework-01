package com.example.otushomework_01.ui.viewmodels

import android.app.Application
import android.view.LayoutInflater
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
    private val _favAdapter : FavoritesAdapter
    private val _moviesAdapter : MoviesAdapter

    // auto-reset flag on read
    var needScrollToMainPage : Boolean = false
        get() {
            return if (field) {
                field = false
                true
            } else {
                false
            }
        }

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

    val favAdapter : LiveData<FavoritesAdapter>
    val moviesAdapter : LiveData<MoviesAdapter>

    init {
        _app.addListener(this)

        _favAdapter = FavoritesAdapter(
            LayoutInflater.from(_app.applicationContext),
            favMovies
        )

        _moviesAdapter = MoviesAdapter(
            LayoutInflater.from(_app.applicationContext),
            movies
        )

        initListeners()

        favAdapter = MutableLiveData(_favAdapter)
        moviesAdapter = MutableLiveData(_moviesAdapter)
    }

    private fun initListeners() {
        _favAdapter.listener = object : FavoritesAdapter.Listener {
            override fun onFavMovieClick(movieItem: MovieItem) {
                needScrollToMainPage = true
                _app.setSelectedMovie(movieItem)
            }
        }

        _moviesAdapter.listener = object : MoviesAdapter.Listener {
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
                _app.addNewMovie()
            }
        }
    }

    fun onMoviesListScrolledToEnd() {
        _app.uploadMovies()
    }

    fun onMovieSwipeToDelete(movie: MovieItem) {
        _app.removeMovie(movie)
    }

    fun onCompleteActions() {
        _likedMovie.value = null
        _detailsMovie.value = null
    }

    fun onLikeCancel(movie: MovieItem) {
        _moviesAdapter.listener?.onMovieFavButtonClick(movie)
    }

    override fun onCleared() {
        _app.removeListener(this)
        super.onCleared()
    }

    override fun onMovieChanged(movie: MovieItem) {
        favMovies.findItem(movie) { i ->
            _favAdapter.notifyItemChanged(i)
        }
        movies.findItem(movie) { i ->
            _favAdapter.notifyItemChanged(i)
        }
    }

    override fun onMovieAdded(movie: MovieItem, i: Int) {
        _moviesAdapter.notifyItemInserted(i)
    }

    override fun onMovieRemoved(movie: MovieItem, i: Int) {
        _moviesAdapter.notifyItemRemoved(i)
        _moviesAdapter.notifyItemRangeChanged(i, _moviesAdapter.itemCount - i)    }

    override fun onFavMovieAdded(movie: MovieItem, i: Int) {
        _favAdapter.notifyItemInserted(i)
    }

    override fun onFavMovieRemoved(movie: MovieItem, i: Int) {
        _favAdapter.notifyItemRemoved(i)
        _favAdapter.notifyItemRangeChanged(i, _favAdapter.itemCount - i)
    }

    override fun onMovieSelected(movie: MovieItem) {
        _selectedMovie.value = movie
    }
}
