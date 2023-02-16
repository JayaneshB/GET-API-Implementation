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
import com.project.netprime.adapter.TvShowAdapter
import com.project.netprime.databinding.FragmentTvShowBinding
import com.project.netprime.models.TvShowResponse
import com.project.netprime.models.TvShow
import com.project.netprime.onClickInterface.OnClickTvShowHandler
import com.project.netprime.services.ApiInterface
import com.project.netprime.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class TvShowFragment : Fragment(), OnClickTvShowHandler {

    private lateinit var binding: FragmentTvShowBinding
    private var tvShowList: List<TvShow> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.rvTvShowList.layoutManager = LinearLayoutManager(context)
            this.rvTvShowList.setHasFixedSize(true)
        }

        getTvShowData { tvShows: List<TvShow> ->

            binding.rvTvShowList.adapter = TvShowAdapter(tvShows, this)

        }

        binding.searchViewBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterSearch(newText)
                return true
            }

        })
    }

    private fun getTvShowData(callBack: (List<TvShow>) -> Unit) {

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        val apiKey = "72feba6dc1a2eda1297a8f778e2eb1d1"
        apiService.getTvList(apiKey).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                if (response.body() != null) {
                    tvShowList = response.body()!!.tvShow
                    return callBack(tvShowList)
                } else {
                    Log.e("Msg", "Error: Tv Show Response is null")
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
            }

        })

    }

    override fun onClickTvShow(pos: TvShow) {

    }

    private fun filterSearch(query: String) {

        if (query != null) {
            val filterList = ArrayList<TvShow>()
            for (i in tvShowList) {
                if (i.tvshow_name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(), "No Results Found", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvTvShowList.adapter = TvShowAdapter(filterList, this)
            }
        } else {
            binding.rvTvShowList.adapter = TvShowAdapter(tvShowList, this)
        }
    }
}