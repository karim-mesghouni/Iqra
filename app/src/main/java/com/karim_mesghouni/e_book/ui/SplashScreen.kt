package com.karim_mesghouni.e_book.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.utils.SharedPref

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            if (getUser())
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 3000)

    }

    private fun getUser():Boolean{
        SharedPref.init(this);
        return SharedPref.read(SharedPref.IS_THERE,false);
    }
}