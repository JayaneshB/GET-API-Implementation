package com.project.netprime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.netprime.MainActivity
import com.project.netprime.adapter.MovieAdapter
import com.project.netprime.databinding.FragmentMovieBinding
import com.project.netprime.models.Movie
import com.project.netprime.models.MovieResponse
import com.project.netprime.services.ApiInterface
import com.project.netprime.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private var TAG = MainActivity::class.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.rvMovieList.layoutManager = LinearLayoutManager(context)
            this.rvMovieList.setHasFixedSize(true)
        }

      getMovieData { movies : List<Movie> ->

          binding.rvMovieList.adapter = MovieAdapter(movies)

      }
    }

    private fun getMovieData(callBack:(List<Movie>) -> Unit) {

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        val apiKey = "72feba6dc1a2eda1297a8f778e2eb1d1"
        apiService.getMovieList(apiKey).enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.body()!=null) {
                    return callBack(response.body()!!.movie)
                }
                else{
                    Log.e(TAG, "Error: movieResponse is null")
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

        })

    }
}