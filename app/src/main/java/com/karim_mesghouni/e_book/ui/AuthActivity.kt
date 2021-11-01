package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.exemple.e_book.ui.SignInFragment
import com.karim_mesghouni.e_book.R
import java.util.*

class AuthActivity:AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()


        val fragmentManager = supportFragmentManager

        fragmentManager.commit {
            setReorderingAllowed(true)
            replace<SignInFragment>(R.id.fragment_container)
        }



    }











}


