package com.example.otushomework_01

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

typealias ClickHandler = (movieItem: MovieItem, position: Int) -> Unit

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private val items: List<MovieItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MOVIE = 0
    }

    private var movieClickListener: ClickHandler? = null
    private var detailsClickListener: ClickHandler? = null

    var selectedMovie = -1
        set(value) {
            if (value in items.indices)  {
                selectMovie(value)
                field = value
            }
        }

    var colorSelected = 0
    var colorBackground = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        log("onCreateViewHolder $viewType")

        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount() = itemIndexToPosition(items.size)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        log("onBindViewHolder $position")
        val i = itemPositionToIndex(position)

        if (i in items.indices) {
            if (holder is MovieItemViewHolder) {
                holder.bind(items[i])
                holder.itemView.setOnClickListener { movieClickListener?.invoke(items[i], position) }
                holder.itemView.buttonDetails.setOnClickListener { detailsClickListener?.invoke(items[i], position) }
            }
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE_MOVIE

    fun setMovieClickListener(listener: ClickHandler) {
        movieClickListener = listener
    }

    fun setDetailsClickListener(listener: ClickHandler) {
        detailsClickListener = listener
    }

    private fun selectMovie(i: Int) {
         if (i != selectedMovie) {
            unselectMovie()
            items[i].colorBackground = colorSelected
            items[i].showDetailsButton = true
            notifyItemChanged(i)
        }
    }

    private fun unselectMovie() {
        if (selectedMovie in items.indices) {
            items[selectedMovie].colorBackground = colorBackground
            items[selectedMovie].showDetailsButton = false
            notifyItemChanged(selectedMovie)
        }
    }

    private fun itemIndexToPosition(i: Int) : Int {
        return i
    }

    private fun itemPositionToIndex(pos: Int) : Int {
        return pos
    }

    private fun log(msg: String) {
        Log.d("MoviesAdapter", msg)
    }
}