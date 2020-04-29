package com.example.otushomework_01

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(
    private val inflater: LayoutInflater,
    private val items: List<MovieItem>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun onFavMovieClick(movieItem: MovieItem)
    }

    private var colorSelected = Color.TRANSPARENT
    private var colorBackground = Color.TRANSPARENT
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteItemViewHolder(inflater.inflate(R.layout.item_favorite_movie, parent, false)).apply {
            colorBackground = this@FavoritesAdapter.colorBackground
            colorSelected = this@FavoritesAdapter.colorSelected
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FavoriteItemViewHolder) {
            val movie = items[position]
            holder.bind(movie)
            holder.itemView.setOnClickListener { listener?.onFavMovieClick(movie) }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        // We need to determine some colors
        colorBackground = (recyclerView.background as? ColorDrawable?)?.color ?: Color.TRANSPARENT
        colorSelected = ContextCompat.getColor(recyclerView.context, R.color.colorSelection)
    }
}