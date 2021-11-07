package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentLibraryBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.adapter.BookAdapter
import com.karim_mesghouni.e_book.ui.adapter.FavListAdapter
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.viewmodels.LibraryViewModel
import com.karim_mesghouni.e_book.viewmodels.LibraryViewModelFactory

/**
 * This [Fragment] represent library that will contain downloads and book market.
 */
class LibraryFragment : Fragment() {
    private lateinit var mAdapter: BookAdapter
    private lateinit var binding: FragmentLibraryBinding
    private val viewModel: LibraryViewModel by lazy {
        requireNotNull(activity)
        val repo: IRepository<Book> =
            Repository(Book::class.java, Constants.BOOK_COLLECTION, requireContext())
        val factory = LibraryViewModelFactory(repo, requireContext())
        ViewModelProvider(this, factory).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false)


        binding.viewModel = viewModel





        binding.favoritesRv.adapter = FavListAdapter()
        binding.favoritesRv.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.downloadedList.observe(viewLifecycleOwner, {
            setUpRv(it)
        })

        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpRv(it: List<Book>?) {
        mAdapter = BookAdapter(it) {


        }


        binding.libraryInclude.booksRvLarge.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.favoritesRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    HomeFragment.listener?.invoke(false)
                } else if (dy < 0) {
                    // show bottom nav
                    HomeFragment.listener?.invoke((true))
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    companion object{
        var listener: ((Boolean)->Unit)? = null
    }
}