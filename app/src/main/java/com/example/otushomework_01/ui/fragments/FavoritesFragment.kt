package com.example.otushomework_01.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.data.findItem
import com.example.otushomework_01.ui.adapters.FavoritesAdapter
import com.example.otushomework_01.ui.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment
    : Fragment(R.layout.fragment_favorites) {

    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        // scroll to selected movie
        model.selectedMovie.observe(viewLifecycleOwner, Observer<MovieItem> {
            model.favMovies.findItem(it) { i ->
                recyclerFav.scrollToPosition(i)
            }
        })

        // update movies list adapter
        model.favAdapter.observe(viewLifecycleOwner, Observer<FavoritesAdapter> {
            recyclerFav.adapter = it
        })
    }

    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerFav.layoutManager = layoutManager
        recyclerFav.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerFav.addItemDecoration(dividerItemDecoration)
    }
}