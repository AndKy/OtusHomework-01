package com.example.otushomework_01

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var movies: List<MovieItem>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies = arguments?.getParcelableArrayList<MovieItem>(ARG_MOVIES)

        initRecycler()
        initClickListeners()

        savedInstanceState?.let {
            val selMovie = it.getInt(STATE_SELECTED_MOVIE, -1)
            val movieAdapter = recyclerMovies.adapter as MoviesAdapter

            movieAdapter.selectedMovie = selMovie
            log("onCreate: _selectedMovie = %d".format(selMovie))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {

            val movieAdapter = recyclerMovies.adapter as MoviesAdapter

            putInt(STATE_SELECTED_MOVIE, movieAdapter.selectedMovie)
            log("onSaveInstanceState: _selectedMovie = %d".format(movieAdapter.selectedMovie))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OUR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val comment = it.getStringExtra(DetailsActivity.STATE_COMMENT)
                    val like = it.getBooleanExtra(DetailsActivity.STATE_LIKE, false)

                    log("onActivityResult: comment='%s'".format(comment))
                    log("onActivityResult: like='%b'".format(like))
                }
            }
        }
    }

    private fun initRecycler() {
        val twoColumns = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        // portrait single column layout, landscape - two columns
        val layoutManager: RecyclerView.LayoutManager =
            if (twoColumns)
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            else
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val moviesAdapter = MoviesAdapter(LayoutInflater.from(context), movies!!)

        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = moviesAdapter
    }

    private fun initClickListeners() {

        val moviesAdapter = recyclerMovies.adapter as MoviesAdapter
        moviesAdapter.setMovieClickListener { movieItem: MovieItem, i: Int ->
            log("MovieClickListener clicked at position $i")

            val pos = movies!!.indexOfFirst { it ===  movieItem }
            moviesAdapter.selectedMovie = pos
        }

        moviesAdapter.setDetailsClickListener { movieItem: MovieItem, i: Int ->
            log("DetailsClickListener clicked at position $i")
            openDetailsWindow(movieItem)
        }
    }

    private fun openDetailsWindow(movieItem: MovieItem) {
        val intent = Intent(context, DetailsActivity::class.java).apply {
            putExtra(STATE_SELECTED_MOVIE, movieItem)
        }

        startActivityForResult(intent, OUR_REQUEST_CODE)
    }

    private fun log(msg: String) {
        Log.d("movies-list-fragment", msg)
    }

    companion object {
        const val OUR_REQUEST_CODE = 1
        const val STATE_SELECTED_MOVIE = "selected_movie"
        const val ARG_MOVIES = "movies"

        fun newInstance(movies: List<MovieItem>) = MovieListFragment().apply {
            arguments = Bundle(1).apply {
                putParcelableArrayList(ARG_MOVIES, ArrayList(movies))
            }
        }
    }
}