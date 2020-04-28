package com.example.otushomework_01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(
    private val inflater: LayoutInflater,
    private val items: MutableList<MovieItem>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieClickListener: ClickHandler? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FavoriteItemViewHolder(inflater.inflate(R.layout.item_favorite_movie, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FavoriteItemViewHolder) {
            val movie = items[position]
            holder.bind(movie)
            holder.itemView.setOnClickListener { movieClickListener?.invoke(movie) }
        }
    }

    fun setMovieClickListener(listener: ClickHandler) {
        movieClickListener = listener
    }

    fun getMoviePosition(movie: MovieItem): Int {
        return items.indexOfFirst { it ===  movie }
    }

    fun updateMovie(movie: MovieItem) {
        val pos = getMoviePosition(movie)
        if (pos in items.indices) {
            notifyItemChanged(pos)
        }
    }
}