package com.karim_mesghouni.e_book.ui

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var finalHost:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
//
//
//        val navController = navHostFragment.navController




         finalHost = NavHostFragment.create(R.navigation.nav_graph)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main,finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()






    }

    override fun onStart() {
        super.onStart()
       binding.navView.setupWithNavController(finalHost.navController)
    }









}