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

typealias ClickHandler = (movieItem: MovieItem, position: Int) -> Unit
typealias AddMovieClickHandler = (position: Int) -> Unit

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private val items: List<MovieItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_BUTTON = 1
    }

    private var movieClickListener: ClickHandler? = null
    private var detailsClickListener: ClickHandler? = null
    private var addMovieClickListener: AddMovieClickHandler? = null

    var selectedMovie = -1
        set(value) {
            if (value in items.indices)  {
                selectMovie(value)
                field = value
            }
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
                holder.bind(items[i])
                holder.itemView.setOnClickListener { movieClickListener?.invoke(items[i], position) }
                holder.itemView.buttonDetails.setOnClickListener { detailsClickListener?.invoke(items[i], position) }
            }
        }
        else if (holder is ButtonsItemViewHolder) {
            holder.itemView.buttonAdd.setOnClickListener { addMovieClickListener?.invoke(position) }
        }
    }

    override fun getItemViewType(position: Int) : Int {
        return if (position < items.size) VIEW_TYPE_MOVIE else VIEW_TYPE_BUTTON
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

    private fun selectMovie(i: Int) {
         if (i != selectedMovie) {
            unselectMovie()
            items[i].colorBackground = colorSelected
            items[i].showDetailsButton = items[i].textAbout.isNotEmpty()
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