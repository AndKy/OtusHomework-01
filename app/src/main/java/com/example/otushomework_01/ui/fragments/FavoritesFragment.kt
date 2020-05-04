package com.example.otushomework_01.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.ui.adapters.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

interface FavoritesFragmentEventHandler {
    fun onMovieAdded(movie: MovieItem, i: Int)
    fun onMovieRemoved(movie: MovieItem, i: Int)
    fun onMovieChanged(movie: MovieItem)
    fun onMovieSelected(movie: MovieItem)
}

class FavoritesFragment
    : Fragment(R.layout.fragment_favorites)
    , FavoritesFragmentEventHandler {

    interface Listener : FavoritesAdapter.Listener

    var listener: Listener? = null
    var movies: List<MovieItem> = listOf()

    private val adapter: FavoritesAdapter
        get() = recyclerFav.adapter as FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initClickListeners()
    }

    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val moviesAdapter =
            FavoritesAdapter(
                LayoutInflater.from(context),
                movies
            )

        recyclerFav.layoutManager = layoutManager
        recyclerFav.adapter = moviesAdapter
        recyclerFav.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerFav.addItemDecoration(dividerItemDecoration)
    }

    private fun initClickListeners() {
        adapter.listener = listener
    }

    override fun onMovieAdded(movie: MovieItem, i: Int) {
        adapter.notifyItemInserted(i)
    }

    override fun onMovieRemoved(movie: MovieItem, i: Int) {
        adapter.notifyItemRemoved(i)
        adapter.notifyItemRangeChanged(i, adapter.itemCount - 1)
    }

    override fun onMovieChanged(movie: MovieItem) {
        val i = movies.indexOfFirst { it === movie }
        if (i in movies.indices)
            adapter.notifyItemChanged(i)
    }

    override fun onMovieSelected(movie: MovieItem) {
        val i = movies.indexOfFirst { it === movie }
        if (i in movies.indices)
            recyclerFav.scrollToPosition(i)
    }

}