package com.karim_mesghouni.e_book.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karim_mesghouni.e_book.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {
    //private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController = findNavController(R.id.fragment_container_main)
       // val navView : BottomNavigationView = findViewById(R.id.bottom_nav)
      //  navView.setupWithNavController(navController)



    }






}