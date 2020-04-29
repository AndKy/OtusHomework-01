package com.example.otushomework_01

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private fun main() = activity as IMainMovieActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initClickListeners()
    }

    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val moviesAdapter = FavoritesAdapter(LayoutInflater.from(context), main().getFavorites())

        recyclerFav.layoutManager = layoutManager
        recyclerFav.adapter = moviesAdapter
        recyclerFav.scrollToPosition(0)
    }

    private fun initClickListeners() {
        val moviesAdapter = recyclerFav.adapter as FavoritesAdapter

        moviesAdapter.setMovieClickListener { movieItem: MovieItem ->
            main().onFavoriteMovieClick(movieItem)
        }
    }

    fun updateMovie(movie: MovieItem) {
        if (recyclerFav != null) {
            val moviesAdapter = recyclerFav.adapter as FavoritesAdapter
            moviesAdapter.updateMovie(movie)

            val pos = moviesAdapter.getMoviePosition(movie)
            if (pos >= 0)
                recyclerFav.scrollToPosition(pos)
        }

    }

    fun onFavoriteMovieAppended() {
        val moviesAdapter = recyclerFav.adapter as FavoritesAdapter
        moviesAdapter.notifyItemInserted(main().getFavorites().size - 1)
    }

    fun onFavoriteMovieRemovedAt(pos: Int) {
        val moviesAdapter = recyclerFav.adapter as FavoritesAdapter
        moviesAdapter.notifyItemRemoved(pos)
    }

}