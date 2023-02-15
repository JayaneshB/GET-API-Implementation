package com.project.netprime.models

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

    ) : Parcelable

@Parcelize
data class TvShow(
    @SerializedName("name")
    val tvshow_name: String,
    @SerializedName("poster_path")
    val tvShow_poster_path: String,
    @SerializedName("overview")
    val tvShow_overview: String,
    @SerializedName("first_air_date")
    val tvShow_releaseDate:String

):Parcelable