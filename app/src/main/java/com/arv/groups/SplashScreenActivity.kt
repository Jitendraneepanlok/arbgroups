package com.arv.groups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.arv.groups.Activity.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    // Splash screen timer
    private val SPLASH_TIME_OUT = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}