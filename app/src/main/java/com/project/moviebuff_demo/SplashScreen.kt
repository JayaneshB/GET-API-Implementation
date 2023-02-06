package com.project.moviebuff_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.project.moviebuff_demo.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this@SplashScreen,R.anim.splash_screen_animation)

        binding.splashTv.startAnimation(animation)

        Handler().postDelayed( {
            startActivity(
                Intent(this@SplashScreen,MainActivity::class.java))
            finish()
        },2000)
    }
}