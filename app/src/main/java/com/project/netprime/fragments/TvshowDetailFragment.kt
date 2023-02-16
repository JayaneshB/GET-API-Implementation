package com.project.netprime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.netprime.R
import com.project.netprime.database.NetPrimeDataBase
import com.project.netprime.database.WishListDetails
import com.project.netprime.databinding.FragmentTvShowBinding
import com.project.netprime.databinding.FragmentTvshowDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvshowDetailFragment : Fragment() {

    private lateinit var binding : FragmentTvshowDetailBinding

    private var imageUrl="https://image.tmdb.org/t/p/w500/"

    companion object {
        private const val TITLE = "title"
        private const val POSTER = "poster"
        private const val STORYLINE = "overview"
        private const val DATE = "date"

        fun newInstance(overview:String,title:String,image:String,date:String):TvshowDetailFragment {

            val tvShowDetailFragment = TvshowDetailFragment()
            val bundle = Bundle()
            bundle.putString(STORYLINE,overview)
            bundle.putString(TITLE,title)
            bundle.putString(POSTER,image)
            bundle.putString(DATE,date)
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
       val date=arguments?.getString(DATE)
        Log.e("","DATE"+date)
        Log.e("","POSTER"+arguments?.getString(POSTER))

        val poster = binding.fragmentTvShowImg
        Glide.with(this).load(imageUrl+arguments?.getString(POSTER)).into(poster)

        binding.btnTvshowFragment.setOnClickListener {

            binding.btnTvshowFragment.text = (resources.getString(R.string.added))
//            binding.btnTvshowFragment.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_check,0)
            Snackbar.make(requireView(), resources.getString(R.string.add_to_wishlist), Snackbar.LENGTH_SHORT).show()
            wishList(date)

        }

        return binding.root
    }

    private fun wishList(date:String?) {

        val database = NetPrimeDataBase.getInstance(requireContext())
        val dao = database?.wishListDao()
        val data = WishListDetails(
            0,
            binding.fragmentTvShowTitle.text.toString(),
            date!!,
            binding.btnTvshowFragment.text.toString(),
        )
        CoroutineScope(Dispatchers.IO).launch {
            dao?.insert(data)
        }

    }
}