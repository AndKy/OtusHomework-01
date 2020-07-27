package com.example.otushomework_01.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otushomework_01.ui.adapters.MoviesAdapter
import com.example.otushomework_01.ui.MoviesSwipeToDelete
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.data.findItem
import com.example.otushomework_01.ui.viewholders.MovieItemViewHolder
import com.example.otushomework_01.ui.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment
    : Fragment(R.layout.fragment_movie_list) {

    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        // scroll to selected movie
        model.selectedMovie.observe(viewLifecycleOwner, Observer<MovieItem> {
            model.movies.findItem(it) { i ->
                if (model.needScrollToSelectedMovie) {
                    model.needScrollToSelectedMovie = false
                    recyclerMovies.smoothScrollToPosition(i)
                }
            }
        })
    }

    private fun initRecycler() {
        val twoColumns = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        // portrait single column layout, landscape - two columns
        val layoutManager: RecyclerView.LayoutManager =
            if (twoColumns)
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            else
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val adapter = MoviesAdapter(
            LayoutInflater.from(context),
            model.movies
        )

        model.onMovieListAdapterCreated(adapter)

        recyclerMovies.adapter = adapter
        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.scrollToPosition(0)

        val swipeHandler = object : MoviesSwipeToDelete(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is MovieItemViewHolder)
                    model.onMovieSwipeToDelete(viewHolder.movie!!)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerMovies)

        recyclerMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val mgr = recyclerView.layoutManager
                val pos =
                    if (mgr is GridLayoutManager)
                        mgr.findLastVisibleItemPosition()
                    else if(mgr is LinearLayoutManager)
                        mgr.findLastCompletelyVisibleItemPosition()
                    else
                        0

                if (layoutManager.itemCount - pos < PAGINATION_THRESHOLD) {
                    model.onMoviesListScrolledToEnd()
                }
            }
        })
    }

    companion object {
        const val PAGINATION_THRESHOLD = 10
    }

}
