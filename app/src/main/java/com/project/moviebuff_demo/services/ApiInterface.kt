package com.project.moviebuff_demo.services

import com.project.moviebuff_demo.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/3/movie/popular?api_key=72feba6dc1a2eda1297a8f778e2eb1d1")
    fun getMovieList() : Call<MovieResponse>
    fun getMovieDetails() : Call<MovieResponse>

}