package com.example.otushomework_01

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var movies = ArrayList<MovieItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies = arguments?.getParcelableArrayList<MovieItem>(ARG_MOVIES)!!

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
                //StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            else
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply { stackFromEnd = true }

        val moviesAdapter = MoviesAdapter(LayoutInflater.from(context), movies)

        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = moviesAdapter
        recyclerMovies.scrollToPosition(0)

        val swipeHandler = object : MoviesSwipeToDelete(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                 moviesAdapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerMovies)
    }

    private fun initClickListeners() {

        val moviesAdapter = recyclerMovies.adapter as MoviesAdapter
        moviesAdapter.setMovieClickListener { movieItem: MovieItem ->
            log("MovieClickListener clicked")

            val pos = movies.indexOfFirst { it ===  movieItem }
            moviesAdapter.selectedMovie = pos
        }

        moviesAdapter.setDetailsClickListener { movieItem: MovieItem ->
            log("DetailsClickListener clicked")
            openDetailsWindow(movieItem)
        }

        moviesAdapter.setAddMovieClickListener {
            log("AddMovieClickListener clicked")

            moviesAdapter.append(Hollywood.makeNewMovie(movies))
            recyclerMovies.smoothScrollToPosition(moviesAdapter.getButtonPosition())
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