package com.unallapps.movieapp.ui.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.unallapps.movieapp.databinding.ActivitySplashScreenBinding
import com.unallapps.movieapp.ui.Login.LoginActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delayMillis: Long = 4000 // 4 saniye
        val handler = Handler()
        handler.postDelayed(
            {
                startActivity(Intent(this, LoginActivity::class.java))
            }, delayMillis
        )
    }
}