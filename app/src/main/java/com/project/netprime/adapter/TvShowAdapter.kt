package com.project.netprime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.netprime.R
import com.project.netprime.databinding.MovieItemBinding
import com.project.netprime.fragments.TvshowDetailFragment
import com.project.netprime.models.TvShow
import com.project.netprime.onClickInterface.OnClickTvShowHandler

class TvShowAdapter(private var tvShow:List<TvShow>,private val onClick : OnClickTvShowHandler) :
RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>(){
    inner class TvShowViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private val imageURL = "https://image.tmdb.org/t/p/w500/"

        fun bindTvShow(tvShow:TvShow) {
            binding.movieTitle.text = tvShow.tvshow_name
            binding.movieReleaseDate.text = tvShow.tvShow_releaseDate
            Glide.with(itemView).load(imageURL + tvShow.tvShow_poster_path).into(binding.moviePoster)
        }

        fun searchFilteredResult(result:List<TvShow>){
            tvShow=result
            notifyDataSetChanged()
        }

        override fun onClick(v: View?) {

            val position = tvShow[bindingAdapterPosition]
            onClick.onClickTvShow(position)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvShow.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = tvShow[position]
        holder.bindTvShow(tvShow)

        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as AppCompatActivity
            val fragment = TvshowDetailFragment.newInstance(
                tvShow.tvShow_overview,tvShow.tvshow_name,tvShow.tvShow_poster_path,tvShow.tvShow_releaseDate)
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.main,fragment).addToBackStack(null).commit()

        }
    }
}