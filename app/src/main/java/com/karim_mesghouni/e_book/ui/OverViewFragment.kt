package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment


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
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check if book in my favorites

        if (arguments != null) {

         val args = this.arguments.run { OverViewFragmentArgs.fromBundle(this!!) }
           book = args.book
        }




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview,container,false)
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

        TextViewEllipsize.makeTextViewResizable(binding.summary,3,"see more",true)
        //binding.setBook(book)
        binding.book = book
    }

    // rememper to save the book


}
