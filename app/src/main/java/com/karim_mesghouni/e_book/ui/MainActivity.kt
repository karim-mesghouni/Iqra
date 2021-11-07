package com.karim_mesghouni.e_book.ui



import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var finalHost:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         finalHost = NavHostFragment.create(R.navigation.nav_graph)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main,finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()






    }

    override fun onStart() {
        super.onStart()
       binding.navView.setupWithNavController(finalHost.navController)
        finalHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.overViewFragment ->{binding.navView.visibility = View.GONE}
                else -> {binding.navView.visibility = View.VISIBLE}
            }
        }

        HomeFragment.listener = {
            Log.d("Lis",it.toString())
            if (it)
                viewVisibleAnimator(binding.navView)
            else
                viewGoneAnimator(binding.navView)
        }

        LibraryFragment.listener = {
            Log.d("Lis",it.toString())
            if (it)
                viewVisibleAnimator(binding.navView)
            else
                viewGoneAnimator(binding.navView)
        }
    }

   private fun viewGoneAnimator(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(500)

            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })

    }

    private fun viewVisibleAnimator(view: View) {
        view.animate()
            .alpha(1f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.VISIBLE
                }
            })


    }









}