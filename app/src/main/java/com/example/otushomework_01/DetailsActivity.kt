package com.example.otushomework_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {

    private var _movie = 0

    private var _images =
        intArrayOf(
            R.drawable.movie_1_big,
            R.drawable.movie_2_big,
            R.drawable.movie_3_big
        )
    private var _titles =
        intArrayOf(
            R.string.movie_1_title,
            R.string.movie_2_title,
            R.string.movie_3_title
        )
    private var _aboutTexts =
        intArrayOf(
            R.string.movie_1_about,
            R.string.movie_2_about,
            R.string.movie_3_about
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        _movie = intent.getIntExtra(MainActivity.STATE_SELECTED_MOVIE, 0)

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewAbout = findViewById<TextView>(R.id.textViewAbout)
        val imageView = findViewById<ImageView>(R.id.imageView)

        textViewTitle.text = getString(_titles[_movie])
        textViewAbout.text = getString(_aboutTexts[_movie])
        imageView.setImageResource(_images[_movie])
    }
}
