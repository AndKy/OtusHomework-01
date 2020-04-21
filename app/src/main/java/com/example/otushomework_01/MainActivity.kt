package com.example.otushomework_01

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private var _selectedMovie: Int = -1
    private var _buttons =
        intArrayOf(
            R.id.buttonDetails1,
            R.id.buttonDetails2,
            R.id.buttonDetails3
        )
    private var _cells =
        intArrayOf(
            R.id.cell1,
            R.id.cell2,
            R.id.cell3
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for ((i, id) in _cells.withIndex()) {
            val layout = findViewById<LinearLayout>(id)
            layout.setOnClickListener {
                selectMovie(i)
            }
        }

        for ((i, id) in _buttons.withIndex()) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener {
                openDetailsWindow(i)
            }
        }

        savedInstanceState?.let {
            val selMovie = it.getInt(STATE_SELECTED_MOVIE, -1)

            selectMovie(selMovie)
            log("onCreate: _selectedMovie = %d".format(selMovie))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {
            putInt(STATE_SELECTED_MOVIE, _selectedMovie)
            log("onSaveInstanceState: _selectedMovie = %d".format(_selectedMovie))
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

    private fun openDetailsWindow(number: Int) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(STATE_SELECTED_MOVIE, number)
        }

        log("openDetailsWindow: number = %d".format(number))
        startActivityForResult(intent, OUR_REQUEST_CODE)
    }

    private fun log(msg: String) {
        Log.d("main", msg)
    }

    private fun setSelectedCellBackground(number: Int) {
        for ((i, id) in _cells.withIndex()) {
            val layout = findViewById<LinearLayout>(id)
            layout.setBackgroundColor(
                if (i == number)
                    ContextCompat.getColor(layout.context, R.color.colorSelection)
                else
                    getBackgroundColor()
            )
        }
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

    private fun displayDetailsButton(number: Int) {
        for ((i, id) in _buttons.withIndex()) {
            val btn = findViewById<Button>(id)
            btn.visibility = if (i == number) Button.VISIBLE else Button.GONE
        }
    }

    private fun selectMovie(number: Int) {
        _selectedMovie = number
        displayDetailsButton(number)
        setSelectedCellBackground(number)

        log("select movie #%d".format(number))
    }

    companion object {
        const val STATE_SELECTED_MOVIE = "selected_movie"
        const val OUR_REQUEST_CODE = 1
    }
}
