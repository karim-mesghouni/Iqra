package com.karim_mesghouni.e_book.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentHomeScreenBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.adapter.BookCategoryAdapter
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.enforceSingleScrollDirection
import com.karim_mesghouni.e_book.utils.getUserId
import com.karim_mesghouni.e_book.viewmodels.HomeViewModel
import com.karim_mesghouni.e_book.viewmodels.HomeViewModelFactory

/**
 * This [Fragment] represent home screen that will show all books .
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding


    //private var categories: MutableList<BookCategory> = mutableListOf()
    private val viewModel: HomeViewModel by lazy {
        requireNotNull(this)
        val repository: IRepository<Book> =
            Repository(Book::class.java, Constants.BOOK_COLLECTION, requireContext())
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

        Log.d("userId", getUserId(requireContext()))
        binding = FragmentHomeScreenBinding.inflate(inflater)

        return binding.root
    }

    // @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readerImage.load(auth.currentUser?.photoUrl)
        val name = auth.currentUser?.displayName?.split(" ")?.get(0)
        binding.greetingReader.text = context?.getString(R.string.greatUser, name)

        // initialize adapter
        categoryAdapter = BookCategoryAdapter(listener = {

            findNavController().navigate(HomeFragmentDirections.showOverView(it))
        }, more = {

        })

//        binding.searchBook.setOnClickListener {
//            findNavController().navigate(HomeFragmentDirections.search())
//        }
        /**
         *  I call newReleases in trendingObserver to keep trending list in the first index and newReleases in the second index
         **/
        // observe trending books
        viewModel.getTrending().observe(viewLifecycleOwner, {
            categoryAdapter.categories.add(it)
            categoryAdapter.notifyDataSetChanged()

            observeNewReleases()
        })
        setUpRv()
    }

    // observe new releases books
    //@SuppressLint("NotifyDataSetChanged")
    private fun observeNewReleases() {
        viewModel.getNewReleases().observe(viewLifecycleOwner, {
            categoryAdapter.categories.add(it)
            categoryAdapter.notifyDataSetChanged()
            observeForYou()
        })
    }

    // observe for you books
    //@SuppressLint("NotifyDataSetChanged")
    private fun observeForYou() {
        viewModel.getForYou().observe(viewLifecycleOwner, {
            categoryAdapter.categories.add(it)
            categoryAdapter.notifyDataSetChanged()
        })
    }


    private fun setUpRv() {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.homeScreenMainRv.rv.run {
            layoutManager = linearLayoutManager
            adapter = categoryAdapter
            enforceSingleScrollDirection()
        }

        binding.homeScreenMainRv.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    listener?.invoke(false)
                } else if (dy < 0) {
                    // show bottom nav
                    listener?.invoke((true))
                }
            }

        })




    }


    companion object {
        var listener: ((Boolean) -> Unit)? = null
    }


}






