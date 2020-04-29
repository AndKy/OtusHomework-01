package com.example.otushomework_01

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem (
    val idImgLogo : Int,
    val idImgScreenshot : Int,
    val textTitle : String,
    val textDescription : String,
    val textAbout : String,
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false
) : Parcelable
