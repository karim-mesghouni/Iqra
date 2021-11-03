package com.karim_mesghouni.e_book.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentHomeScreenBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.adapter.BookCategoryAdapter
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.enforceSingleScrollDirection
import com.karim_mesghouni.e_book.viewmodels.HomeViewModel
import com.karim_mesghouni.e_book.viewmodels.HomeViewModelFactory
import io.github.glailton.expandabletextview.ExpandableTextView

/**
 * This [Fragment] represent home screen that will show all books .
 */
class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeScreenBinding

    //private var categories: MutableList<BookCategory> = mutableListOf()
    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this)
        val repository: IRepository<Book> = Repository(Book::class.java, Constants.BOOK_COLLECTION)
        //get instance of the viewModelFactory
        val viewModelFactory = HomeViewModelFactory(repository, requireContext())
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }
    private lateinit var categoryAdapter: BookCategoryAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readerImage.load(auth.currentUser?.photoUrl)
        val name = auth.currentUser?.displayName?.split(" ")?.get(0)
        binding.greetingReader.text = context?.getString(R.string.greatUser, name)

        // initialize adapter
        categoryAdapter = BookCategoryAdapter(listener = {
            Log.d("book","Book clicked")
            findNavController().navigate(HomeFragmentDirections.showOverView(it))
        },more = {

        })



        viewModel.getTrending().observe(viewLifecycleOwner, Observer<BookCategory> {
            categoryAdapter.categories.add(it)
            categoryAdapter.notifyDataSetChanged()
            observeNewReleases()
        })








        setUpRv()
    }
    private fun observeNewReleases(){
        viewModel.getNewReleases().observe(viewLifecycleOwner, Observer<BookCategory> {
            categoryAdapter.categories.add(it)
            categoryAdapter.notifyDataSetChanged()
            observeForYou()
        })
    }

    private fun observeForYou(){

    }



    private fun setUpRv() {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.homeScreenMainRv.rv.run {
            layoutManager = linearLayoutManager
            adapter = categoryAdapter
            enforceSingleScrollDirection()
        }

    }










}






