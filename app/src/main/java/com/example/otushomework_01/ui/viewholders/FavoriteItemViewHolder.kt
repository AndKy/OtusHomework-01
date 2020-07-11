package com.example.otushomework_01.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.R
import com.example.otushomework_01.data.assign
import kotlinx.android.synthetic.main.item_favorite_movie.view.*
import kotlinx.android.synthetic.main.item_favorite_movie.view.imageLogo
import kotlinx.android.synthetic.main.item_favorite_movie.view.textViewSubtitle
import kotlinx.android.synthetic.main.item_favorite_movie.view.textViewTitle
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var movie: MovieItem? = null
        private set

    fun bind(item: MovieItem) {
        movie = item

        itemView.imageLogo.assign(item.urlImgLogo)

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
