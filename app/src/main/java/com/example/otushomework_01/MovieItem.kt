package com.example.otushomework_01

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem (
    val idImgLogo : Int,
    val idImgScreenshot : Int,
    val idTextTitle : Int,
    val idTextDescription : Int,
    val idTextAbout : Int,
    var colorBackground: Int = 0,
    var showDetailsButton: Boolean = false
) : Parcelable
