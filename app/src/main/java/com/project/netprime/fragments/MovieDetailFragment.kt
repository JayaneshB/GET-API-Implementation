package com.project.netprime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.project.netprime.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    private var imageUrl = "https://image.tmdb.org/t/p/w500/"

    companion object {

        private const val TITLE = "title"
        private const val POSTER = "image"
        private const val STORYLINE = "overview"

        fun newInstance(overview:String , title:String,image:String):MovieDetailFragment {

            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()

            bundle.putString(STORYLINE, overview)
            bundle.putString(TITLE, title)
            bundle.putString(POSTER, image)
            movieDetailFragment.arguments = bundle

            return movieDetailFragment

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater,container,false)

        binding.fragmentMovieTitle.text=arguments?.getString(TITLE)
        binding.fragmentMovieOverviewTv.text = arguments?.getString(STORYLINE)
        val poster = binding.fragmentMovieImg
        Glide.with(this).load(imageUrl+arguments?.getString(POSTER)).into(poster)


        return binding.root
    }

}