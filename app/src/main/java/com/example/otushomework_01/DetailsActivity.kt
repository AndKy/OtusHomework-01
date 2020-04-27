package com.example.otushomework_01

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.otushomework_01.MovieListFragment.Companion.STATE_SELECTED_MOVIE

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = intent.getParcelableExtra<MovieItem>(STATE_SELECTED_MOVIE)

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewAbout = findViewById<TextView>(R.id.textViewAbout)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val btnInvite = findViewById<Button>(R.id.buttonInvite)

        textViewTitle.text = movie.textTitle
        textViewAbout.text = movie.textAbout
        imageView.setImageResource(movie.idImgScreenshot)

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
        val btnReturnComment = findViewById<Button>(R.id.buttonReturnComment)

        savedInstanceState?.let {
            checkBoxLike.isChecked = it.getBoolean(STATE_LIKE, false)
            editTextComment.text.append(it.getString(STATE_COMMENT, ""))

            log("onCreate: like = %b comment_len=%d".format(checkBoxLike.isChecked, editTextComment.text.length))
        }

        btnReturnComment.setOnClickListener {
            val intent = Intent().apply {
                putExtra(STATE_LIKE, checkBoxLike.isChecked)
                putExtra(STATE_COMMENT, editTextComment.text.toString())

                log("onClick: like = %b, comment_len=%d".format(checkBoxLike.isChecked, editTextComment.text.length))
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
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
        const val STATE_LIKE = "like"
        const val STATE_COMMENT = "comment"
    }

}
