package com.wiryadev.bajpmovie.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.wiryadev.bajpmovie.databinding.ActivitySplashscreenBinding
import com.wiryadev.bajpmovie.utils.Constants.Companion.DELAY_SPLASH

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var activitySplashscreenBinding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(activitySplashscreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, DELAY_SPLASH)
    }

}