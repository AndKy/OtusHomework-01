package com.example.otushomework_01

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
