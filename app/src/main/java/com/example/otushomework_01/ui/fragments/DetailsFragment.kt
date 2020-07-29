package com.example.otushomework_01.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.data.assign
import com.example.otushomework_01.ui.activities.MainActivity
import com.example.otushomework_01.ui.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val model: DetailsViewModel by activityViewModels()
    var movie = MovieItem()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(toolbar)

        toolbar.title = movie.textTitle
        textViewAbout.text = movie.textAbout
        imageView.assign(movie.urlImgScreenshot)
        checkBoxLike.isChecked = model.like
        editTextComment.text.append(model.comment)

        initClickListeners()
    }

    private fun initClickListeners() {
        checkBoxLike.setOnClickListener {
            model.like = checkBoxLike.isChecked
        }

        editTextComment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.comment = editTextComment.text.toString()
            }
        })

        buttonInvite.setOnClickListener {
            inviteFriends(movie)
        }

        buttonReturnComment.setOnClickListener {
            model.onReturnCommentClicked()
            requireActivity().supportFragmentManager.popBackStack()

            Toast.makeText(context, getString(R.string.thanks_for_feedback), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        model.onDestroy()
        super.onDestroy()
    }

    fun inviteFriends(movie: MovieItem) {
        val inviteMsg = getString(R.string.invite_msg).format(movie.textTitle)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, inviteMsg)
        }

        sendIntent.resolveActivity(requireActivity().packageManager)?.let {
            requireActivity().startActivity(sendIntent)
        }
    }
}