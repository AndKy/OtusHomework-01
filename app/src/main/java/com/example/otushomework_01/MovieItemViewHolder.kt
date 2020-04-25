package com.example.otushomework_01

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: MovieItem) {
        // Implicit findViewById<>
        itemView.imageLogo.setImageResource(item.idImgLogo)
        itemView.textViewDescription.setText(item.idTextDescription)
        itemView.textViewTitle.setText(item.idTextTitle)
        itemView.item.setBackgroundColor(item.colorBackground)
        itemView.buttonDetails.visibility = if (item.showDetailsButton) View.VISIBLE else View.GONE
    }
}
