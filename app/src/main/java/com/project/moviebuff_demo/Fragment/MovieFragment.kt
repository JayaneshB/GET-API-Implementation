package com.project.moviebuff_demo.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.moviebuff_demo.R

class MovieFragment : Fragment() {

    companion object {
        private const val TITLE = "title"
        private const val POSTER = "image"
        private const val STORYLINE = "overview"
        private const val TAG: String = "MovieFragment"
        fun newInstance(overView: String, title: String, image: String): MovieFragment {

            val movieFragment = MovieFragment()
            val bundle = Bundle()

            bundle.putString(STORYLINE, overView)
            bundle.putString(TITLE, title)
            bundle.putString(POSTER, image)
            movieFragment.arguments = bundle

            return movieFragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Checking the value with log.

        Log.d(TAG, "title: ${arguments?.getString(TITLE)}")
        Log.d(TAG,"OverView: ${arguments?.getString(STORYLINE)}")
        Log.d(TAG,"Poster: ${arguments?.getString(POSTER)}")

        */




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }
}