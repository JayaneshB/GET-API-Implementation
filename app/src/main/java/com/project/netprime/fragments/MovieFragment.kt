package com.project.netprime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.netprime.MainActivity
import com.project.netprime.adapter.MovieAdapter
import com.project.netprime.databinding.FragmentMovieBinding
import com.project.netprime.models.Movie
import com.project.netprime.models.MovieResponse
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
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.rvMovieList.layoutManager = LinearLayoutManager(context)
            this.rvMovieList.setHasFixedSize(true)
        }

        getMovieData { movies: List<Movie> ->

            binding.rvMovieList.adapter = MovieAdapter(movies, this@MovieFragment)

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
                if (response.body() != null) {
                    movieList =response.body()!!.movie
                    return callBack(movieList)
                } else {
                    Log.e(TAG, "Error: movieResponse is null")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })
    }

    override fun onClickMovie(pos: Movie) {

    }

    private fun filterSearch(query: String?) {

        if (query != null) {
            val filterList = ArrayList<Movie>()
            for (i in movieList) {
                if (i.original_title.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(),"No Results Found",Toast.LENGTH_SHORT).show()
            }
            else {
                binding.rvMovieList.adapter = MovieAdapter(filterList,this)
            }
        }
        else{
            binding.rvMovieList.adapter = MovieAdapter(movieList,this)
        }
    }
}
