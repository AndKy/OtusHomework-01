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

    private var favorites = ArrayList<MovieItem>()
    private var clickHandler: ClickHandler? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorites = (activity as MainActivity).movies

        initRecycler()
        initClickListeners()
    }


    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val moviesAdapter = FavoritesAdapter(LayoutInflater.from(context), favorites)

        recyclerFav.layoutManager = layoutManager
        recyclerFav.adapter = moviesAdapter
        recyclerFav.scrollToPosition(0)
    }

    private fun initClickListeners() {
        val moviesAdapter = recyclerFav.adapter as FavoritesAdapter

        moviesAdapter.setMovieClickListener { movieItem: MovieItem ->
            clickHandler?.invoke(movieItem)
        }
    }

    fun setOnClickListener(listener: ClickHandler) {
        clickHandler = listener
    }
}