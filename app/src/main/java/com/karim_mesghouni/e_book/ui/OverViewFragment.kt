package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs


import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentOverviewBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.helpers.TextViewEllipsize

/**
 * This [Fragment] will show the detailed information about a selected book.
 */
class OverViewFragment : Fragment() {
    //private lateinit var viewModel: OverviewViewModel
    private lateinit var book: Book
    val args : OverViewFragmentArgs by navArgs()
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check if book in my favorites

            book = args.book
            book.isfav = true

           Log.d("book",book.toString())






    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_overview,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readBook.setOnClickListener {
           // downloadFile(getReference("books"),book.name?.lowercase()!!,activity?.applicationContext!!)
        }


        binding.listenBook.setOnClickListener {
            Toast.makeText(context,R.string.notyet,Toast.LENGTH_SHORT).show()
        }

        binding.overviewAddFav.setOnClickListener {
            if (book.isfav!!){
                binding.overviewAddFav.setImageResource(R.drawable.ic_bookmark)
                book.isfav = false
            }else{
                binding.overviewAddFav.setImageResource(R.drawable.ic_bookmark_black)
                book.isfav = true
            }
        }

        binding.readBook.setOnClickListener {

        }
        //TextViewEllipsize.makeTextViewResizable(binding.overviewBookTitle,10,"...",true)

        //TextViewEllipsize.makeTextViewResizable(binding.summary,5,"see more",true)
        //binding.setBook(book)
        binding.book = book
    }

    // rememper to save the book


}
