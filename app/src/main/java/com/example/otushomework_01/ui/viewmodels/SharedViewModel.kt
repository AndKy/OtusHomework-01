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
                selectMovie(movieItem)
            }
        }

    }

    override fun onCleared() {
        _app.removeListener(this)
        super.onCleared()
    }

    fun selectMovie(item: MovieItem) {
        _app.setSelectedMovie(item)
    }

    override fun onMovieChanged(movie: MovieItem) {
        favMovies.findItem(movie) { i ->
            _favAdapter.notifyItemChanged(i)
        }
    }

    override fun onMovieAdded(movie: MovieItem, i: Int) {
    }

    override fun onMovieRemoved(movie: MovieItem, i: Int) {
    }

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
