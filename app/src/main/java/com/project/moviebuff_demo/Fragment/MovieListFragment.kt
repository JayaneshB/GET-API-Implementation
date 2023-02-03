package com.project.moviebuff_demo.Fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.moviebuff_demo.Interface.OnClickHandler
import com.project.moviebuff_demo.adapter.MovieAdapter
import com.project.moviebuff_demo.databinding.FragmentMovieListBinding
import com.project.moviebuff_demo.models.Movie
import com.project.moviebuff_demo.models.MovieResponse
import com.project.moviebuff_demo.services.ApiInterface
import com.project.moviebuff_demo.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "Fragment"


class MovieListFragment : Fragment(), OnClickHandler {

    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        screenView()

        binding.btnRetry.setOnClickListener {
            screenView()
        }
        binding.swipeRefresh.setOnRefreshListener {
            screenView()
            binding.swipeRefresh.isRefreshing=false
        }

        binding.apply {
            this.rvMovieList.layoutManager = LinearLayoutManager(context)
            this.rvMovieList.setHasFixedSize(true)
        }
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.body() != null) {
                    return callback(response.body()!!.movie)
                } else {
                    Log.e(TAG, "Error: movieResponse is null")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })
    }

    override fun onClick(pos: Movie) {

    }
    private fun networkAvailable(): Boolean {

        val check = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCheck = check.getNetworkCapabilities(check.activeNetwork)
        return (networkCheck != null && networkCheck.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))

    }
    private fun screenView() {

        with(binding) {
            if (networkAvailable()) {
                internetState.visibility = View.VISIBLE
                noInternetLayout.visibility = View.GONE
                getMovieData { movies: List<Movie> ->
                    if (movies.isNotEmpty()) {
                        binding.rvMovieList.adapter = MovieAdapter(movies, this@MovieListFragment)
                    } else {
                        Log.e(TAG, "Error: movieResponse is null")
                    }
                }
            } else {
                noInternetLayout.visibility = View.VISIBLE
                internetState.visibility = View.GONE
            }
        }
    }
}
