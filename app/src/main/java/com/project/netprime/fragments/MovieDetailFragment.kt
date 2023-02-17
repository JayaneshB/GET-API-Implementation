package com.project.netprime.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.netprime.R
import com.project.netprime.database.NetPrimeDataBase
import com.project.netprime.database.WishListDetails
import com.project.netprime.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    private var imageUrl = "https://image.tmdb.org/t/p/w500/"


    companion object {

        private const val TITLE = "title"
        private const val POSTER = "image"
        private const val STORYLINE = "overview"
        private const val DATE = "date"

        fun newInstance(overview:String , title:String,image:String,date:String):MovieDetailFragment {

            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()

            bundle.putString(STORYLINE, overview)
            bundle.putString(TITLE, title)
            bundle.putString(POSTER, image)
            bundle.putString(DATE,date)
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
        val date=arguments?.getString(DATE)
        Glide.with(this).load(imageUrl+arguments?.getString(POSTER)).into(poster)


        binding.btnMovieFragment.setOnClickListener {

            binding.btnMovieFragment.text = (resources.getString(R.string.added))
//            binding.btnMovieFragment.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_check,0)
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
            binding.fragmentMovieTitle.text.toString(),
            date!!,
            binding.btnMovieFragment.text.toString()
        )
        CoroutineScope(Dispatchers.IO).launch {
            dao?.insert(data)
        }
    }
}