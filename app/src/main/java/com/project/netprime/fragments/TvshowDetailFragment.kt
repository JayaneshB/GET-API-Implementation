package com.project.netprime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.project.netprime.R
import com.project.netprime.databinding.FragmentTvShowBinding
import com.project.netprime.databinding.FragmentTvshowDetailBinding

class TvshowDetailFragment : Fragment() {

    private lateinit var binding : FragmentTvshowDetailBinding

    private var imageUrl="https://image.tmdb.org/t/p/w500/"

    companion object {
        private const val TITLE = "title"
        private const val POSTER = "poster"
        private const val STORYLINE = "overview"

        fun newInstance(overview:String,title:String,image:String):TvshowDetailFragment {

            val tvShowDetailFragment = TvshowDetailFragment()
            val bundle = Bundle()
            bundle.putString(STORYLINE,overview)
            bundle.putString(TITLE,title)
            bundle.putString(POSTER,image)
            tvShowDetailFragment.arguments=bundle

            return tvShowDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvshowDetailBinding.inflate(inflater,container,false)

        binding.fragmentTvShowTitle.text = arguments?.getString(TITLE)
        binding.fragmentTvShowOverviewTv.text = arguments?.getString(STORYLINE)
        val poster = binding.fragmentTvShowImg
        Glide.with(this).load(imageUrl+arguments?.getString(POSTER)).into(poster)


        return binding.root
    }
}