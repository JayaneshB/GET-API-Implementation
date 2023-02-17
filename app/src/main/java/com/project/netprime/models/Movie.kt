package com.project.netprime.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("original_title")
    val original_title: String,
    @SerialName("release_date")
    val release_date: String,
    @SerialName("poster_path")
    val poster_path: String,
    @SerialName("overview")
    val movie_overview: String,

    )

@Serializable
data class TvShow(
    @SerialName("name")
    val tvshow_name: String,
    @SerialName("poster_path")
    val tvShow_poster_path: String,
    @SerialName("overview")
    val tvShow_overview: String,
    @SerialName("first_air_date")
    val tvShow_releaseDate:String

)