package com.project.moviebuff_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.moviebuff_demo.Fragment.MovieListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, MovieListFragment(), "movie_list_fragment")
            .commit()
    }
}