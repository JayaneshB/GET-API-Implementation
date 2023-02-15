package com.project.moviebuff_demo.Fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.moviebuff_demo.R
import com.project.moviebuff_demo.databinding.FragmentMovieBinding


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val imageURL = "https://image.tmdb.org/t/p/w500/"

    companion object {
        private const val TITLE = "title"
        private const val POSTER = "image"
        private const val STORYLINE = "overview"

        //        private const val TAG: String = "MovieFragment"
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        /**
         * Checking the value with log.

        Log.d(TAG, "title: ${arguments?.getString(TITLE)}")
        Log.d(TAG,"OverView: ${arguments?.getString(STORYLINE)}")
        Log.d(TAG,"Poster: ${arguments?.getString(POSTER)}")

         */

        /**
         *  Added the required detail of the movie
         *  with respect to fields using bundles
         */

        binding.fragmentTvTitle.text = arguments?.getString(TITLE)
        binding.fragmentOverview.text = arguments?.getString(STORYLINE)

        val poster = binding.fragmentImage
        Glide.with(this).load(imageURL + arguments?.getString(POSTER)).into(poster)

        /**
         *  Performed onclick event for the button
         */


        binding.wishListButton.setOnClickListener {

            binding.wishListButton.text = resources.getString(R.string.added)
            binding.wishListButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check,0)

            Snackbar.make(requireView(), "Added to wishlist", Snackbar.LENGTH_SHORT).show()
            Handler().postDelayed({ parentFragmentManager.popBackStack() }, 3000)

        }

//        findNavController().navigate(R.id.action_movieFragment_to_movieListFragment)

        return binding.root
    }
}