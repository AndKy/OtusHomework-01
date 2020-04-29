package com.example.otushomework_01

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var colorSelected = Color.TRANSPARENT
    var colorBackground = Color.TRANSPARENT

    var movie: MovieItem? = null
        private set

    fun bind(item: MovieItem) {
        movie = item

        // Implicit findViewById<>
        itemView.imageLogo.setImageResource(item.idImgLogo)
        itemView.textViewDescription.text = item.textDescription

        val title = item.textTitle.split("\n")
        if (title.size == 1) {
            itemView.textViewTitle.text = item.textTitle
            itemView.textViewSubtitle.text = ""
            itemView.textViewSubtitle.visibility = View.GONE
        } else {
            itemView.textViewTitle.text = title[0]
            itemView.textViewSubtitle.text = title[1]
            itemView.textViewSubtitle.visibility = View.VISIBLE
        }

        itemView.toggleFav.isChecked = item.isFavorite
        itemView.buttonDetails.visibility = if (item.isSelected) View.VISIBLE else View.GONE
        itemView.item.setBackgroundColor(
            if (item.isSelected)
                colorSelected
            else
                colorBackground
        )
    }
}
