package com.example.otushomework_01.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otushomework_01.ui.adapters.MoviesAdapter
import com.example.otushomework_01.ui.MoviesSwipeToDelete
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.ui.viewholders.MovieItemViewHolder
import kotlinx.android.synthetic.main.fragment_movie_list.*

interface MovieListFragmentEventHandler {
    fun onMovieAdded(movie: MovieItem, i: Int)
    fun onMovieRemoved(movie: MovieItem, i: Int)
    fun onMovieChanged(movie: MovieItem)
    fun onMovieSelected(movie: MovieItem)
}

class MovieListFragment
    : Fragment(R.layout.fragment_movie_list)
    , MovieListFragmentEventHandler {

    interface Listener : MoviesAdapter.Listener {
        fun onMovieSwipeDelete(movieItem: MovieItem)
    }

    var listener: Listener? = null
    var movies: List<MovieItem> = listOf()

    private val adapter: MoviesAdapter
        get() = recyclerMovies.adapter as MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initClickListeners()
    }

    private fun initRecycler() {
        val twoColumns = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        // portrait single column layout, landscape - two columns
        val layoutManager: RecyclerView.LayoutManager =
            if (twoColumns)
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            else
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply { stackFromEnd = true }

        val moviesAdapter =
            MoviesAdapter(
                LayoutInflater.from(context),
                movies
            )

        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = moviesAdapter
        recyclerMovies.scrollToPosition(0)

        val swipeHandler = object : MoviesSwipeToDelete(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is MovieItemViewHolder)
                    listener?.onMovieSwipeDelete(viewHolder.movie!!)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerMovies)
    }

    private fun initClickListeners() {
        adapter.listener = listener
    }

    override fun onMovieAdded(movie: MovieItem, i: Int) {
        adapter.notifyItemInserted(i)
    }

    override fun onMovieRemoved(movie: MovieItem, i: Int) {
        adapter.notifyItemRemoved(i)
        adapter.notifyItemRangeChanged(i, movies.size - 1)
    }

    override fun onMovieChanged(movie: MovieItem) {
        val i = movies.indexOfFirst { it === movie }
        if (i in movies.indices)
            adapter.notifyItemChanged(i)
    }

    override fun onMovieSelected(movie: MovieItem) {
        val i = movies.indexOfFirst { it === movie }
        if (i in movies.indices)
            recyclerMovies.smoothScrollToPosition(i)
    }
}
