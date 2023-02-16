package com.project.netprime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.netprime.R
import com.project.netprime.databinding.MovieItemBinding
import com.project.netprime.fragments.MovieDetailFragment
import com.project.netprime.models.Movie
import com.project.netprime.onClickInterface.OnClickMovieHandler

class MovieAdapter(private var movies: List<Movie>,private val onClick : OnClickMovieHandler) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        private val imageURL = "https://image.tmdb.org/t/p/w500/"

        fun bindMovie(movie: Movie) {
            binding.movieTitle.text = movie.original_title
            binding.movieReleaseDate.text = movie.release_date
            Glide.with(itemView).load(imageURL + movie.poster_path).into(binding.moviePoster)
        }

        fun searchFilteredResult(result:List<Movie>) {
            movies=result
            notifyDataSetChanged()
        }

        override fun onClick(v: View?) {
            val pos = movies[bindingAdapterPosition]
            onClick.onClickMovie(pos)
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
        val movie = movies[position]
        holder.bindMovie(movie)

        holder.itemView.setOnClickListener{
            val activity = holder.itemView.context as AppCompatActivity
            val fragment = MovieDetailFragment.newInstance(
                movie.movie_overview, movie.original_title, movie.poster_path,movie.release_date
            )
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.main, fragment).addToBackStack(null).commit()

        }

    }
}
