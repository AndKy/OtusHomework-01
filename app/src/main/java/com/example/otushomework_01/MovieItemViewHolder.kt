package com.example.otushomework_01

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: MovieItem) {
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

        itemView.item.setBackgroundColor(item.colorBackground)
        itemView.buttonDetails.visibility = if (item.showDetailsButton) View.VISIBLE else View.GONE
    }
}
