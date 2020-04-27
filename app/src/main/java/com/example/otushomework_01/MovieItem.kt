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
    var colorBackground: Int = 0,
    var showDetailsButton: Boolean = false
) : Parcelable
