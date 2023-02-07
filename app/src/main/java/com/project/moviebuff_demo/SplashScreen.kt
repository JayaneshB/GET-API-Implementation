package com.project.moviebuff_demo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.animation.AnimationUtils
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.project.moviebuff_demo.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this@SplashScreen,R.anim.splash_screen_animation)

        binding.splashTv.startAnimation(animation)


        Glide.with(this@SplashScreen).load(R.drawable.ic_launcher).into(binding.gifImg)

        Handler().postDelayed( {
            startActivity(
                Intent(this@SplashScreen,MainActivity::class.java))
            finish()
        },5000)
    }
}
