package com.project.moviebuff_demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.moviebuff_demo.R
import com.project.moviebuff_demo.models.Movie

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val Image_URL = "https://image.tmdb.org/t/p/w500/"

        fun bindMovie(movie: Movie) {

            val title: TextView = itemView.findViewById(R.id.movie_title)
            val date: TextView = itemView.findViewById(R.id.movie_releaseDate)
            val poster: ImageView = itemView.findViewById(R.id.movie_Poster)

            title.text = movie.original_title
            date.text = movie.release_date

            /**
             * Glide is used for loading the images from the API to the view
             */
            Glide.with(itemView).load(Image_URL + movie.poster_path).into(poster)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}