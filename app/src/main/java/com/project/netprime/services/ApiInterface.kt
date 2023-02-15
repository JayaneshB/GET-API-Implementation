package com.project.netprime.services

import com.project.netprime.models.MovieResponse
import com.project.netprime.models.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("/3/movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("/3/tv/popular")
    fun getTvList(
        @Query("api_key") apiKey: String
    ): Call<TvShowResponse>

}