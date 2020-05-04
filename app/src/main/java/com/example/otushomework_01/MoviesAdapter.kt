package com.example.otushomework_01

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_button.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private val items: List<MovieItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_BUTTON = 1
    }

    interface Listener {
        fun onMovieClick(movieItem: MovieItem)
        fun onMovieFavButtonClick(movieItem: MovieItem)
        fun onMovieDetailsButtonClick(movieItem: MovieItem)
        fun onAddMovieButtonClick()
    }

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        log("onCreateViewHolder $viewType")

        return if (viewType == VIEW_TYPE_MOVIE)
            MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
        else
            ButtonsItemViewHolder(inflater.inflate(R.layout.item_button, parent, false))
    }

    override fun getItemCount() = items.size + 1 // +1 button

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        log("onBindViewHolder $position")

        if (holder is MovieItemViewHolder) {
            if (position in items.indices) {
                val movie = items[position]
                holder.bind(movie)
                holder.itemView.setOnClickListener { listener?.onMovieClick(movie) }
                holder.itemView.buttonDetails.setOnClickListener { listener?.onMovieDetailsButtonClick(movie) }
                holder.itemView.toggleFav.setOnClickListener { listener?.onMovieFavButtonClick(movie) }
            }
        }
        else if (holder is ButtonsItemViewHolder) {
            holder.itemView.buttonAdd.setOnClickListener { listener?.onAddMovieButtonClick() }
        }
    }

    override fun getItemViewType(position: Int) : Int {
        return if (position < items.size) VIEW_TYPE_MOVIE else VIEW_TYPE_BUTTON
    }

    private fun log(msg: String) {
        Log.d("MoviesAdapter", msg)
    }
}