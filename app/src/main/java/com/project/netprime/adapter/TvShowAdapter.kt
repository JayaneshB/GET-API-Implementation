package com.project.netprime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.netprime.databinding.MovieItemBinding
import com.project.netprime.models.TvShow

class TvShowAdapter(private val tvShow:List<TvShow>) :
RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>(){
    class TvShowViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root) {

        private val imageURL = "https://image.tmdb.org/t/p/w500/"

        fun bindTvShow(tvShow:TvShow) {
            binding.movieTitle.text = tvShow.tvshow_name
            binding.movieReleaseDate.text = tvShow.tvShow_releaseDate
            Glide.with(itemView).load(imageURL + tvShow.tvShow_poster_path).into(binding.moviePoster)
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
        holder.bindTvShow(tvShow.get(position))
    }
}