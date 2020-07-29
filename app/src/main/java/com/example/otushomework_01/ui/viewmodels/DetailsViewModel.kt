package com.example.otushomework_01.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.otushomework_01.data.MovieApplication

class DetailsViewModel(application: Application)
    : AndroidViewModel(application) {

    private val _app = application as MovieApplication

    var comment = ""
    var like = false

    fun onReturnCommentClicked() {
        Log.d("onReturnCommentClicked", "comment='%s'".format(comment))
        Log.d("onReturnCommentClicked", "like='%b'".format(like))
    }

    fun onDestroy() {
        comment = ""
        like = false
    }
}