package com.example.otushomework_01.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    interface Listener {
        fun onInviteButtonClicked(movie: MovieItem)
        fun onReturnCommentClicked(movie: MovieItem, result: Bundle)
    }

    var movie = MovieItem()
    var listener: Listener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewTitle.text = movie.textTitle
        textViewAbout.text = movie.textAbout
        imageView.setImageResource(movie.idImgScreenshot)

        initClickListeners()

        savedInstanceState?.let {
            checkBoxLike.isChecked = it.getBoolean(STATE_LIKE, false)
            editTextComment.text.append(it.getString(STATE_COMMENT, ""))

            log("onCreate: like = %b comment_len=%d".format(checkBoxLike.isChecked, editTextComment.text.length))
        }
    }

    private fun initClickListeners() {
        buttonInvite.setOnClickListener {
            listener?.onInviteButtonClicked(movie)
        }

        buttonReturnComment.setOnClickListener {
            val results = Bundle().apply {
                putBoolean(STATE_LIKE, checkBoxLike.isChecked)
                putString(STATE_COMMENT, editTextComment.text.toString())
            }

            listener?.onReturnCommentClicked(movie, results)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

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