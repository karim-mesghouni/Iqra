package com.karim_mesghouni.e_book.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentSearchBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.adapter.ListAdapter
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.viewmodels.SearchViewModel
import com.karim_mesghouni.e_book.viewmodels.SearchViewModelFactory

class SearchFragment : Fragment() {
    private var data:List<Book> = emptyList()
    private lateinit var madapter:ListAdapter
    private lateinit var binding : FragmentSearchBinding
    private val viewModel: SearchViewModel by lazy {
        requireNotNull(activity)
        val repo: IRepository<Book> =
            Repository(Book::class.java, Constants.BOOK_COLLECTION, requireContext())
        val factory = SearchViewModelFactory(repo)
        ViewModelProvider(this,factory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        madapter = ListAdapter(R.layout.search_item){
            findNavController().navigate(SearchFragmentDirections.showBookFromSearch(it))
            Log.d("nav","called")
        }
        binding = FragmentSearchBinding.inflate(inflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchRv.apply {
            adapter =madapter
            layoutManager = LinearLayoutManager(context)
        }
         viewModel.bookList.observe(viewLifecycleOwner,{
             data = it

         })


        //binding.search.isSubmitButtonEnabled = true


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                madapter.submitList(data.filter { it.name?.contains(query!!)!! })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty())
                   madapter.submitList(emptyList())
                else
                    madapter.submitList(data.filter { it.name?.lowercase()?.startsWith(newText.lowercase())!! })


                return true
            }

        })

         binding.searchRv.apply {
             adapter =madapter
             layoutManager = LinearLayoutManager(context)
         }


    }



}