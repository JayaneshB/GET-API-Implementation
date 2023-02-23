package com.project.netprime.fragments

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.netprime.MainActivity
import com.project.netprime.R
import com.project.netprime.adapter.MovieAdapter
import com.project.netprime.databinding.FragmentMovieBinding
import com.project.netprime.models.Movie
import com.project.netprime.models.MovieResponse
import com.project.netprime.network.NetworkChangeReceiver
import com.project.netprime.onClickInterface.OnClickMovieHandler
import com.project.netprime.services.ApiInterface
import com.project.netprime.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MovieFragment : Fragment(), OnClickMovieHandler {

    private lateinit var binding: FragmentMovieBinding
    private var movieList: List<Movie> = emptyList()
    private var TAG = MainActivity::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Register network state change receiver
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(NetworkChangeReceiver(view), intentFilter)

        binding.apply {
            this.rvMovieList.layoutManager = LinearLayoutManager(context)
            this.rvMovieList.setHasFixedSize(true)
        }

        /**
         * Used SavedInstance to store and restore the data
         */

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("MOVIE_LIST")!!
            binding.rvMovieList.adapter = MovieAdapter(movieList, this@MovieFragment)
        }

        getMovieData { movies: List<Movie> ->
            if (movies.isNotEmpty()) {
                binding.rvMovieList.adapter = MovieAdapter(movies, this@MovieFragment)
            } else {
                Toast.makeText(requireContext(), "Failed to get Movie Data", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        binding.swipeRefresh.setOnRefreshListener {
            screenView()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.searchViewBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearch(newText)
                return true
            }

        })
    }

    private fun getMovieData(callBack: (List<Movie>) -> Unit) {

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        val apiKey = "72feba6dc1a2eda1297a8f778e2eb1d1"
        apiService.getMovieList(apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        movieList = it.movie
                        callBack(movieList)
                    }
                } else {
                    Log.e(TAG, "Error: movieResponse is null")
                    callBack(emptyList())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get Movie Data")
                callBack(emptyList())
            }
        })
    }

    override fun onClickMovie(pos: Movie) {

        Navigation.findNavController(requireView()).navigate(R.id.action_movieFragment3_to_movieDetailFragment3)

    }

    private fun filterSearch(query: String?) {

        if (query != null) {
            val filterList = ArrayList<Movie>()
            for (i in movieList) {
                if (i.original_title.lowercase(Locale.ROOT)
                        .contains(query.lowercase(Locale.ROOT))
                ) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(), "No Results Found", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvMovieList.adapter = MovieAdapter(filterList, this)
            }
        } else {
            binding.rvMovieList.adapter = MovieAdapter(movieList, this)
        }
    }

    private fun networkAvailable(): Boolean {

        val check = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCheck = check.getNetworkCapabilities(check.activeNetwork)
        return (networkCheck != null && networkCheck.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))

    }

     fun screenView() {

        with(binding) {
            if (networkAvailable()) {
                progressBar.visibility = View.VISIBLE
                activeState.visibility = View.VISIBLE
                inactiveState.visibility = View.GONE
                getMovieData { movies: List<Movie> ->
                    if (movies.isNotEmpty()) {
                        rvMovieList.adapter = MovieAdapter(movies, this@MovieFragment)
                    } else {
                        Log.e(TAG, "Error:Movie response is null")
                    }
                    progressBar.visibility = View.GONE
                }
            } else {
                inactiveState.visibility = View.VISIBLE
                activeState.visibility = View.GONE
            }
        }
    }
}
