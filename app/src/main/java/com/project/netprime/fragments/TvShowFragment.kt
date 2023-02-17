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
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.netprime.MainActivity
import com.project.netprime.adapter.TvShowAdapter
import com.project.netprime.databinding.FragmentTvShowBinding
import com.project.netprime.models.TvShowResponse
import com.project.netprime.models.TvShow
import com.project.netprime.network.NetworkChangeReceiver
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
    private var TAG = MainActivity::class.java.simpleName

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

        // Register network state change receiver
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(NetworkChangeReceiver(view), intentFilter)

        binding.apply {
            this.rvTvShowList.layoutManager = LinearLayoutManager(context)
            this.rvTvShowList.setHasFixedSize(true)
        }

        /**
         * Used SavedInstance to store and restore the data
         */

        if(savedInstanceState!=null) {
            tvShowList = savedInstanceState.getParcelableArrayList("TVSHOW_LIST")!!
            binding.rvTvShowList.adapter=TvShowAdapter(tvShowList,this@TvShowFragment)
        }

        getTvShowData { tvShows: List<TvShow> ->
            if (tvShows.isNotEmpty()) {
                binding.rvTvShowList.adapter = TvShowAdapter(tvShows, this)
            } else {
                Toast.makeText(requireContext(), "Failed to get Tv Show Data", Toast.LENGTH_SHORT).show()
            }
        }


        binding.swipeRefresh.setOnRefreshListener {
            screenView()
            binding.swipeRefresh.isRefreshing=false
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
                if (response.isSuccessful) {
                    response.body()?.let {
                        tvShowList = it.tvShow
                        callBack(tvShowList)
                    }
                } else {
                    Log.e(TAG, "Error: Tv Show Response is null")
                    callBack(emptyList())
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get Tv Show Data", t)
                callBack(emptyList())
            }
        })
    }


    override fun onClickTvShow(pos: TvShow) {

    }

    private fun filterSearch(query: String) {

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
    }

    private fun networkAvailable(): Boolean {

        val check = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCheck = check.getNetworkCapabilities(check.activeNetwork)
        return (networkCheck != null && networkCheck.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))

    }

    private fun screenView() {

        with(binding) {
            if (networkAvailable()) {
                progressBar.visibility = View.VISIBLE
                activeState.visibility = View.VISIBLE
                inactiveState.visibility = View.GONE

                getTvShowData { tvShows: List<TvShow> ->
                    if (tvShows.isNotEmpty()) {
                        rvTvShowList.adapter = TvShowAdapter(tvShows, this@TvShowFragment)
                    } else {
                        Log.e(TAG, "Error : TvShow Response is null")
                    }
                }
                progressBar.visibility = View.GONE
            }
            else {
                inactiveState.visibility=View.VISIBLE
                activeState.visibility=View.GONE
            }
        }
    }
}