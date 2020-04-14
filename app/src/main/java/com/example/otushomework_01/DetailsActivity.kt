package com.example.otushomework_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

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
        val btnInvite = findViewById<Button>(R.id.buttonInvite)

        textViewTitle.text = getString(_titles[_movie])
        textViewAbout.text = getString(_aboutTexts[_movie])
        imageView.setImageResource(_images[_movie])

        btnInvite.setOnClickListener {
            val inviteMsg = getString(R.string.invite_msg).format(textViewTitle.text)
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, inviteMsg)
            }

            sendIntent.resolveActivity(packageManager)?.let {
                startActivity(sendIntent)
            }
        }

        val checkBoxLike = findViewById<CheckBox>(R.id.checkBoxLike)
        val editTextComment = findViewById<EditText>(R.id.editTextComment)

        savedInstanceState?.let {
            checkBoxLike.isChecked = it.getBoolean(STATE_LIKE, false)
            editTextComment.text.append(it.getString(STATE_COMMENT, ""))

            log("onCreate: like = %b comment_len=%d".format(checkBoxLike.isChecked, editTextComment.text.length))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val checkBoxLike = findViewById<CheckBox>(R.id.checkBoxLike)
        val editTextComment = findViewById<EditText>(R.id.editTextComment)

        outState.apply {
            putBoolean(STATE_LIKE, checkBoxLike.isChecked)
            putString(STATE_COMMENT, editTextComment.text.toString())

            log("onSaveInstanceState: like = %b, comment_len=%d".format(checkBoxLike.isChecked, editTextComment.text.length))
        }
    }

    private fun log(msg: String) {
        Log.d("details:", msg)
    }


    companion object {
        private const val STATE_LIKE = "like"
        private const val STATE_COMMENT = "comment"
    }

}
