package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.karim_mesghouni.e_book.ui.adapter.BookCategoryAdapter
import com.karim_mesghouni.e_book.ui.adapter.OnBookClick
import com.karim_mesghouni.e_book.ui.adapter.OnClickMore

import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.enforceSingleScrollDirection
import com.karim_mesghouni.e_book.viewmodels.HomeViewModel
import com.karim_mesghouni.e_book.viewmodels.HomeViewModelFactory

/**
 * This [Fragment] represent home screen that will show all books .
 */
class HomeFragment : Fragment(), OnBookClick, OnClickMore {
    private var categories:MutableList<BookCategory> = mutableListOf()
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryAdapter: BookCategoryAdapter
    private lateinit var recyclerView :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository : IRepository<Book> = Repository(Book::class.java, Constants.BOOK_COLLECTION)
        //get instance of the viewModelFactory
        val viewModelFactory = HomeViewModelFactory(repository,requireContext())
        // initialize the ViewModel class
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)


         // get trending list
//        viewModel.trendingList.observe(viewLifecycleOwner,{
//            categories.add(it)
//        })
        // get new releases list
//        viewModel.newReleasesList.observe(viewLifecycleOwner,{
//            categories.add(it)
//        })
        // get for you list
//        viewModel.forYouList.observe(viewLifecycleOwner,{
//            categories.add(it)
//        })

        // initialize adapter

       // categoryAdapter = BookCategoryAdapter(categories,this,this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home_screen,container,false)
        recyclerView = view.findViewById(R.id.home_screen_main_rv)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
    }

    private fun setUpRv(){
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = categoryAdapter
            enforceSingleScrollDirection()
        }

    }

    override fun onClick(book: Book?) {

       findNavController().navigate(
           HomeFragmentDirections.showDetails(book!!)
       )
    }

    override fun onClick(category: String) {
        TODO("Not yet implemented")
    }


}




