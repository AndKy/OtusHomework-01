package com.example.otushomework_01

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible

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

    private fun openDetailsWindow(number: Int) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(STATE_SELECTED_MOVIE, number)
        }

        log("openDetailsWindow: number = %d".format(number))
        startActivity(intent)
    }

    private fun log(msg: String) {
        Log.d("msg", msg)
    }

    private fun setSelectedCellBackground(number: Int) {
        for ((i, id) in _cells.withIndex()) {
            val layout = findViewById<LinearLayout>(id)
            layout.setBackgroundColor(
                if (i == number)
                    Color.parseColor("#d0d0ff")
                else
                    Color.WHITE
            )
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
    }
}
