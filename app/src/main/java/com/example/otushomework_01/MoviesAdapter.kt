package com.example.otushomework_01

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_button.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

typealias ClickHandler = (movieItem: MovieItem) -> Unit
typealias LongClickHandler = (movieItem: MovieItem) -> Boolean
typealias AddMovieClickHandler = () -> Unit

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private val items: MutableList<MovieItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_BUTTON = 1
    }

    private var movieClickListener: ClickHandler? = null
    private var detailsClickListener: ClickHandler? = null
    private var addMovieClickListener: AddMovieClickHandler? = null
    private var movieLongClickListener: LongClickHandler? = null

    var selectedMovie = -1
        set(value) {
            selectMovie(value)
            field = value
        }

    private var colorSelected = Color.TRANSPARENT
    private var colorBackground = Color.TRANSPARENT

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        // We need to determine some colors
        colorBackground = (recyclerView.background as? ColorDrawable?)?.color ?: Color.TRANSPARENT
        colorSelected = ContextCompat.getColor(recyclerView.context, R.color.colorSelection)
    }

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
            val i = itemPositionToIndex(position)

            if (i in items.indices) {
                val movie = items[i]
                holder.bind(movie)
                holder.itemView.setOnClickListener { movieClickListener?.invoke(movie) }
                holder.itemView.buttonDetails.setOnClickListener { detailsClickListener?.invoke(movie) }
                holder.itemView.setOnLongClickListener {  movieLongClickListener?.invoke(movie) ?: false }
            }
        }
        else if (holder is ButtonsItemViewHolder) {
            holder.itemView.buttonAdd.setOnClickListener { addMovieClickListener?.invoke() }
        }
    }

    override fun getItemViewType(position: Int) : Int {
        return if (position < items.size) VIEW_TYPE_MOVIE else VIEW_TYPE_BUTTON
    }

    fun removeAt(position: Int) {
        selectedMovie = -1

        val i = itemPositionToIndex(position)
        if (i in items.indices) {
            items.removeAt(i)
            notifyItemRemoved(position)
        }
    }

    fun setMovieClickListener(listener: ClickHandler) {
        movieClickListener = listener
    }

    fun setDetailsClickListener(listener: ClickHandler) {
        detailsClickListener = listener
    }

    fun setAddMovieClickListener(listener: AddMovieClickHandler) {
        addMovieClickListener = listener
    }

    fun setMovieLongClickListener(listener: LongClickHandler) {
        movieLongClickListener = listener
    }

    fun getItemPosition(item: MovieItem): Int {
        val pos = items.indexOfFirst { it ===  item }
        return itemIndexToPosition(pos)
    }

    fun getButtonPosition(): Int {
        return itemIndexToPosition(items.size)
    }

    private fun selectMovie(i: Int) {
         if (i != selectedMovie) {
            unselectMovie()
             if (i in items.indices) {
                 items[i].colorBackground = colorSelected
                 items[i].showDetailsButton = items[i].textAbout.isNotEmpty()
                 notifyItemChanged(i)
             }
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