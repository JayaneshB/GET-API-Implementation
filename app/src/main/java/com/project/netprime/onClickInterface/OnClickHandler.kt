package com.project.netprime.onClickInterface

import com.project.netprime.models.Movie
import com.project.netprime.models.TvShow

interface OnClickMovieHandler {
    fun onClickMovie(pos:Movie)
}
interface OnClickTvShowHandler {
    fun onClickTvShow(pos:TvShow)
}