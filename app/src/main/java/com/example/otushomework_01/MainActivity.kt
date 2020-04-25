package com.example.otushomework_01

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.CharacterPickerDialog
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val _movies = mutableListOf(
        MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, R.string.movie_1_title, R.string.movie_1_desc, R.string.movie_1_about),
        MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, R.string.movie_2_title, R.string.movie_2_desc, R.string.movie_2_about),
        MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, R.string.movie_3_title, R.string.movie_3_desc, R.string.movie_3_about),
        MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, R.string.movie_1_title, R.string.movie_1_desc, R.string.movie_1_about),
        MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, R.string.movie_2_title, R.string.movie_2_desc, R.string.movie_2_about),
        MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, R.string.movie_3_title, R.string.movie_3_desc, R.string.movie_3_about),
        MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, R.string.movie_1_title, R.string.movie_1_desc, R.string.movie_1_about),
        MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, R.string.movie_2_title, R.string.movie_2_desc, R.string.movie_2_about),
        MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, R.string.movie_3_title, R.string.movie_3_desc, R.string.movie_3_about),
        MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, R.string.movie_1_title, R.string.movie_1_desc, R.string.movie_1_about),
        MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, R.string.movie_2_title, R.string.movie_2_desc, R.string.movie_2_about),
        MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, R.string.movie_3_title, R.string.movie_3_desc, R.string.movie_3_about)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClickListeners()

        savedInstanceState?.let {
            val selMovie = it.getInt(STATE_SELECTED_MOVIE, -1)
            val movieAdapter = recyclerMovies.adapter as MoviesAdapter

            movieAdapter.selectedMovie = selMovie
            log("onCreate: _selectedMovie = %d".format(selMovie))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {

            val movieAdapter = recyclerMovies.adapter as MoviesAdapter

            putInt(STATE_SELECTED_MOVIE, movieAdapter.selectedMovie)
            log("onSaveInstanceState: _selectedMovie = %d".format(movieAdapter.selectedMovie))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OUR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val comment = it.getStringExtra(DetailsActivity.STATE_COMMENT)
                    val like = it.getBooleanExtra(DetailsActivity.STATE_LIKE, false)

                    log("onActivityResult: comment='%s'".format(comment))
                    log("onActivityResult: like='%b'".format(like))
                }
            }
        }
    }

    override fun onBackPressed() {

        val dialog : Dialog = object : Dialog(this) {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.dialog_close)

                val btnYes = findViewById<Button>(R.id.buttonYes)
                val btnNo = findViewById<Button>(R.id.buttonNo)

                btnYes.setOnClickListener() {
                    finish()
                }

                btnNo.setOnClickListener() {
                    dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun initClickListeners() {

        // Day-night scheme button
        val btn = findViewById<ImageButton>(R.id.buttonDayNight)
        btn.setOnClickListener {
            setNightMode(!isNightMode())
        }

        val moviesAdapter = recyclerMovies.adapter as MoviesAdapter
        moviesAdapter.setMovieClickListener { movieItem: MovieItem, i: Int ->
            log("MovieClickListener clicked at position $i")

            val pos = _movies.indexOfFirst { it ===  movieItem }
            moviesAdapter.selectedMovie = pos
        }

        moviesAdapter.setDetailsClickListener { movieItem: MovieItem, i: Int ->
            log("DetailsClickListener clicked at position $i")
            openDetailsWindow(movieItem)
        }
    }

    private fun initRecycler() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerMovies)
        val twoColumns = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        // portrait single column layout, landscape - two columns
        val layoutManager: RecyclerView.LayoutManager =
            if (twoColumns)
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            else
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val moviesAdapter = MoviesAdapter(LayoutInflater.from(this), _movies)

        moviesAdapter.colorSelected = ContextCompat.getColor(this, R.color.colorSelection)
        moviesAdapter.colorBackground = getBackgroundColor()

        recycler.layoutManager = layoutManager
        recycler.adapter = moviesAdapter
    }

    private fun openDetailsWindow(movieItem: MovieItem) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(STATE_SELECTED_MOVIE, movieItem)
        }

        startActivityForResult(intent, OUR_REQUEST_CODE)
    }

    private fun log(msg: String) {
        Log.d("main", msg)
    }

    private fun getBackgroundColor() : Int {
        val a = TypedValue()
        theme.resolveAttribute(android.R.attr.windowBackground, a, true)
        if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) { // windowBackground is a color
            return a.data
        } else { // windowBackground is not a color, probably a drawable
            throw Exception("Background type is drawable, but expected color")
        }
    }

    private fun setNightMode(enable: Boolean) {
        val mode =
            if (enable)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun isNightMode() : Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {
        const val STATE_SELECTED_MOVIE = "selected_movie"
        const val OUR_REQUEST_CODE = 1
    }
}
