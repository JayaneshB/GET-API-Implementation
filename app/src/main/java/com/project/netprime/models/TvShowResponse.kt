package com.project.netprime.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponse(
    @SerialName("results")
    val tvShow: List<TvShow>
)
