package com.karim_mesghouni.e_book.ui


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentOverviewBinding
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.helpers.checkNetwork
import com.karim_mesghouni.e_book.helpers.download
import com.karim_mesghouni.e_book.helpers.open
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.viewmodels.OverviewViewModel
import com.karim_mesghouni.e_book.viewmodels.OverviewViewModel.Companion.TAG
import com.karim_mesghouni.e_book.viewmodels.OverviewViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

/**
 * This [Fragment] will show the detailed information about a selected book.
 */
class OverViewFragment : Fragment() {
    private val viewModel: OverviewViewModel by lazy {
        requireNotNull(this)
        val repository: IRepository<Book> =
            Repository(Book::class.java, Constants.BOOK_COLLECTION, requireContext())
        //get instance of the viewModelFactory
        val viewModelFactory =
            OverviewViewModelFactory(book, activity?.applicationContext!!, repository)
        ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)
    }
    private lateinit var book: Book
    private val args: OverViewFragmentArgs by navArgs()
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get the selected book from args
        book = args.book

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_overview, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        var isDownloaded = false
       viewModel.isDownloaded.observe(viewLifecycleOwner,{
           isDownloaded = it
       })
        binding.readBook.setOnClickListener {

            if (!isDownloaded) {
                if (checkNetwork(context)) {
                    checkPermission(this, book)
                    viewModel.setDown(true)
                } else {
                    Toast.makeText(context, R.string.check_your_connection, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, R.string.already_downloaded, Toast.LENGTH_SHORT)
                    .show()
            }


        }
        binding.backOverview.setOnClickListener {
            findNavController().popBackStack()
        }



        viewModel.favList.observe(viewLifecycleOwner, {
            if (it.contains(book.name))
                viewModel.setFav(true)

        })

        binding.overviewAddFav.setOnClickListener {
            Log.d(TAG, "fav button clicked")
            if (viewModel.isFav.value!!) viewModel.setFav(false) else viewModel.setFav(true)
        }
        viewModel.isFav.observe(viewLifecycleOwner, {
            // app:fav = "@{safeUnbox(viewModel.isFav)}"
            Log.d(TAG, "book is in $it")
        })

        viewModel.book.observe(viewLifecycleOwner, {
            Log.d(TAG, "book observed")
            binding.book = it
        })

        binding.lifecycleOwner = viewLifecycleOwner


    }


}

private fun checkPermission(fragment: Fragment, book: Book) {

    Dexter.withContext(fragment.activity?.applicationContext)
        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                download(fragment.requireActivity(), book)
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                response.requestedPermission
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest,
                token: PermissionToken
            ) {

            }
        }).check()

}
