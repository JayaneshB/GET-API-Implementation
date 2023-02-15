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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.moviebuff_demo.Interface.OnClickHandler
import com.project.moviebuff_demo.R
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
    private var movieList: List<Movie>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Used SavedInstance to store and restore the data
         */

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("MOVIE_LIST")
            if (movieList != null) {
                binding.rvMovieList.adapter = MovieAdapter(movieList!!, this@MovieListFragment)
            }
        } else {
            screenView()
        }

        /**
         *  Used SwipeToRefresh to refresh the screen based on the network state
         */

        binding.swipeRefresh.setOnRefreshListener {
            screenView()
            binding.swipeRefresh.isRefreshing = false
        }

        /**
         *  Used LinearLayoutManager to view the list
         */

        binding.apply {
            this.rvMovieList.layoutManager = LinearLayoutManager(context)
            this.rvMovieList.setHasFixedSize(true)
        }

//        findNavController().navigate(R.id.action_movieListFragment_to_movieFragment)
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

    /**
     *  Function for defining which layout should be visible
     */
    private fun screenView() {

        with(binding) {
            if (networkAvailable()) {
                progressBar.visibility = View.VISIBLE
                internetState.visibility = View.VISIBLE
                noInternetLayout.visibility = View.GONE
                getMovieData { movies: List<Movie> ->
                    if (movies.isNotEmpty()) {
                        binding.rvMovieList.adapter = MovieAdapter(movies, this@MovieListFragment)
                    } else {
                        Log.e(TAG, "Error: movieResponse is null")
                    }
                    progressBar.visibility = View.GONE
                }

            } else {
                noInternetLayout.visibility = View.VISIBLE
                internetState.visibility = View.GONE
            }
        }
    }
}
