package com.project.moviebuff_demo.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.moviebuff_demo.R

class MovieFragment : Fragment() {

//    companion object {
//        fun newInstance(overView: String) {
//            val moviFragment = MovieFragment()
//            val bundle = Bundle()
//            bundle.putString("Description", overView)
//            moviFragment.arguments = bundle
//        }
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }


}