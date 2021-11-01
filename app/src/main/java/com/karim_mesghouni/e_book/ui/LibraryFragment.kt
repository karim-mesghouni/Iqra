package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.karim_mesghouni.e_book.R

/**
 * This [Fragment] represent library that will contain downloads and book market.
 */
class LibraryFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library,container,false)
    }
}