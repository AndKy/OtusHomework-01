package com.example.otushomework_01

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_favorite_movie.view.*

class FavoriteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var movie: MovieItem? = null
        private set

    fun bind(item: MovieItem) {
        movie = item

        itemView.imageLogo.setImageResource(item.idImgLogo)

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

        itemView.textViewNumber.text = "#%d".format(adapterPosition + 1)

        if (item.isSelected)
            itemView.setBackgroundResource(R.color.colorSelection)
        else if(adapterPosition % 2 == 0)
            itemView.setBackgroundResource(R.color.colorBackgroundAlt)
        else
            itemView.setBackgroundResource(R.color.colorBackground)
    }
}
