package com.project.moviebuff_demo

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.View
import com.project.moviebuff_demo.Fragment.MovieListFragment
import com.project.moviebuff_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        layout()
//
//        binding.btnRetry.setOnClickListener {
//            layout()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, MovieListFragment(), "movie_list_fragment")
                .commit()
        }

    }

//    private fun networkAvaiable():Boolean {
//
//        val check = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkCheck = check.getNetworkCapabilities(check.activeNetwork)
//
//        return (networkCheck!=null && networkCheck.hasCapability(NET_CAPABILITY_INTERNET))
//
//    }

//    private fun layout() {
//
//        if(networkAvaiable()) {
//
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main, MovieListFragment(), "movie_list_fragment")
//                .commit()
//
//            binding.noInternetLayout.visibility = View.GONE
//
//        }
//        else {
//            binding.noInternetLayout.visibility= View.VISIBLE
//            binding.main.visibility = View.GONE
//        }
//
//    }

