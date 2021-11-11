package com.karim_mesghouni.e_book.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentLibraryBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.helpers.open
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.adapter.BookAdapter
import com.karim_mesghouni.e_book.ui.adapter.ListAdapter
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.viewmodels.LibraryViewModel
import com.karim_mesghouni.e_book.viewmodels.LibraryViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

/**
 * This [Fragment] represent library that will contain downloads and book market.
 */
class LibraryFragment : Fragment() {
    private lateinit var mAdapter: BookAdapter
    private lateinit var binding: FragmentLibraryBinding
    private var localUri:MutableMap<String,String>? = mutableMapOf()
    private val viewModel: LibraryViewModel by lazy {
        requireNotNull(activity)
        val repo: IRepository<Book> =
            Repository(Book::class.java, Constants.BOOK_COLLECTION, requireContext())
        val factory = LibraryViewModelFactory(repo, activity?.applicationContext!!)
        ViewModelProvider(this, factory).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Dexter.withContext(requireContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                      viewModel.loadLocalData()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                          p0?.requestedPermission
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }


            }).check()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false)


        binding.viewModel = viewModel






        binding.favoritesRv.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    private fun checkPe():Boolean{
      return  ContextCompat.checkSelfPermission(activity?.applicationContext!!,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritesRv.adapter = ListAdapter(R.layout.book_item_fav){
            findNavController().navigate(LibraryFragmentDirections.showBookFromFav(it))
        }
        binding.backLibrary.setOnClickListener {
            findNavController().popBackStack()
        }
         viewModel.localDownloadedList.observe(viewLifecycleOwner,{
             localUri = it?: mutableMapOf()
         })

        viewModel.downloadedList.observe(viewLifecycleOwner, {
            setUpRv(it)
            binding.downloadsShimmerFrameLayout.stopShimmer()
            binding.downloadsShimmerFrameLayout.visibility = View.GONE
        })

        viewModel.favList.observe(viewLifecycleOwner,{
            binding.favShimmerFrameLayout.stopShimmer()
            binding.favShimmerFrameLayout.visibility = View.GONE
            binding.favorites.text = getString(R.string.favorites)
        })

        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpRv(it: List<Book>?) {
        mAdapter = BookAdapter(it,mLayout = R.layout.book_item_large) {
           if (checkPe()) {
               open(localUri?.get(it.name), requireContext())
               Log.d("p","permission allowed")
           }else{
               Log.d("p","permission not allowed")
               check()
           }

        }

        binding.libraryInclude.categoryLarge.text = getString(R.string.downloads)
        binding.libraryInclude.booksRvLarge.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.libraryInclude.moreBookLarg.visibility = View.GONE

        binding.favoritesRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    // hide bottom nav
                    HomeFragment.listener?.invoke(false)
                } else if (dy < 0) {
                    // show bottom nav
                    HomeFragment.listener?.invoke((true))
                }
            }


        })

    }

    companion object{
        var listener: ((Boolean)->Unit)? = null
    }

    override fun onResume() {
        super.onResume()
        binding.downloadsShimmerFrameLayout.startShimmer()
        binding.favShimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.downloadsShimmerFrameLayout.stopShimmer()
        binding.favShimmerFrameLayout.stopShimmer()
    }


    private fun check(){
        Dexter.withContext(requireContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    viewModel.loadLocalData()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    p0?.requestedPermission
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }


            }).check()
    }




}