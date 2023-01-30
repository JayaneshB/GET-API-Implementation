package com.project.moviebuff_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.moviebuff_demo.adapter.MovieAdapter
import com.project.moviebuff_demo.databinding.ActivityMainBinding
import com.project.moviebuff_demo.models.Movie
import com.project.moviebuff_demo.models.MovieResponse
import com.project.moviebuff_demo.services.ApiInterface
import com.project.moviebuff_demo.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovieList.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvMovieList.setHasFixedSize(true)

        getMovieData { movies : List<Movie > ->
            if(movies.isNotEmpty()) {
                binding.rvMovieList.adapter = MovieAdapter(movies)
            }
            else {
                Log.e("MainActivity", "Error: movieResponse is null")
            }
        }

    }

    fun getMovieData(callback: (List<Movie>) -> Unit) {

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.body()!=null) {
                    return callback(response.body()!!.movie)
                }
                else {
                    Log.e("MainActivity", "Error: movieResponse is null")
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

        })

    }
}