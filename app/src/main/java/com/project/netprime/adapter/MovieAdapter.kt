package com.project.netprime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.netprime.databinding.MovieItemBinding
import com.project.netprime.models.Movie

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root) {

        private val imageURL = "https://image.tmdb.org/t/p/w500/"

        fun bindMovie(movie: Movie) {
            binding.movieTitle.text = movie.original_title
            binding.movieReleaseDate.text = movie.release_date
            Glide.with(itemView).load(imageURL + movie.poster_path).into(binding.moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }


}