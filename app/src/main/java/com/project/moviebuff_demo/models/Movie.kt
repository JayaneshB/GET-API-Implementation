package com.project.moviebuff_demo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("overview")
    val movie_overview: String,

):Parcelable {

//    constructor() : this("","","","")
}