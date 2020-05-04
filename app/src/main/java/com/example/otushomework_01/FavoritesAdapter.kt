package com.example.otushomework_01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(
    private val inflater: LayoutInflater,
    private val items: List<MovieItem>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun onFavMovieClick(movieItem: MovieItem)
    }

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteItemViewHolder(inflater.inflate(R.layout.item_favorite_movie, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FavoriteItemViewHolder) {
            val movie = items[position]
            holder.bind(movie)
            holder.itemView.setOnClickListener { listener?.onFavMovieClick(movie) }
        }
    }
}