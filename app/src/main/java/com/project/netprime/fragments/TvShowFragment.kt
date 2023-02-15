package com.project.netprime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class TvShowFragment : Fragment(),OnClickTvShowHandler {

    private lateinit var binding : FragmentTvShowBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(inflater,container,false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.rvTvShowList.layoutManager=LinearLayoutManager(context)
            this.rvTvShowList.setHasFixedSize(true)
        }

        getTvShowData { tvShows : List<TvShow> ->

            binding.rvTvShowList.adapter = TvShowAdapter(tvShows,this)

        }
    }

    private fun getTvShowData(callBack: (List<TvShow>) -> Unit) {

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        val apiKey = "72feba6dc1a2eda1297a8f778e2eb1d1"
        apiService.getTvList(apiKey).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                if(response.body()!=null) {
                    return callBack(response.body()!!.tvShow)
                }
                else {
                    Log.e("Msg","Error: Tv Show Response is null")
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
            }

        })

    }

    override fun onClickTvShow(pos: TvShow) {

    }


}