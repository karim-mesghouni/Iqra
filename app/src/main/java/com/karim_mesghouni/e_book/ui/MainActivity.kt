package com.karim_mesghouni.e_book.ui



import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.File


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

            if (it)
                binding.navView.visibility = View.VISIBLE
            else
               binding.navView.visibility = View.GONE
        }

        LibraryFragment.listener = {

            if (it)
                binding.navView.visibility = View.VISIBLE
            else
                binding.navView.visibility = View.GONE
        }
    }

   private fun viewGoneAnimator(view: View) {
        view.animate()

            .alpha(0f)


            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })

    }

    private fun viewVisibleAnimator(view: View) {
        view.animate()
            .alpha(1f)

            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.VISIBLE
                }
            })
    }
}