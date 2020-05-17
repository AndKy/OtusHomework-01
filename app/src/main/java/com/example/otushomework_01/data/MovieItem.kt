package com.example.otushomework_01.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem (
    val idImgLogo : Int = 0,
    val idImgScreenshot : Int = 0,
    val textTitle : String = "",
    val textDescription : String = "",
    val textAbout : String = "",
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false
) : Parcelable
