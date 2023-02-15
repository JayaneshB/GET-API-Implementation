package com.project.netprime.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowResponse(
    @SerializedName("results")
    val tvShow: List<TvShow>
):Parcelable
