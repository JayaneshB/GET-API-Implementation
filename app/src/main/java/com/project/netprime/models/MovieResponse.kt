package com.project.netprime.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("results")
    val movie: List<Movie>,
)

