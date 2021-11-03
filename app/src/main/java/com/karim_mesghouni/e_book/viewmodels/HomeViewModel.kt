package com.karim_mesghouni.e_book.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.utils.SharedPref

/**
 * The [ViewModel] that is associated with the [HomeFragment].
 */
class HomeViewModel(private val repository: IRepository<Book>, context: Context) : ViewModel() {

    private val _trendingList: MutableLiveData<BookCategory> by lazy {
        MutableLiveData<BookCategory>().also {
            loadTrending()
        }
    }
    /**
     * fetch trending books and put them in _trending
     */
    private fun loadTrending() {
        repository.getByCategory("Trending").addOnCompleteListener {
            Log.d("Task", "Completed")
            _trendingList.value = BookCategory(books = it.result, title = "Trending")
        }
    }

    /**
     * return live data contain BookCategory that contain Trending books
     */
    fun getTrending():LiveData<BookCategory>{
        return _trendingList
    }





    private val _newReleasesList : MutableLiveData<BookCategory> by lazy {
        MutableLiveData<BookCategory>().also {
            loadNewReleases()
        }
    }
    /**
     * fetch newReleases books and put them in _newReleases
     */
    private fun loadNewReleases() {
        repository.getByCategory("New Releases").addOnCompleteListener {
            _newReleasesList.value = BookCategory(books = it.result, title = "New Releases")
        }
    }

    fun getNewReleases():LiveData<BookCategory>{
        return _newReleasesList
    }





    private fun getUserId(context: Context): String {
        SharedPref.init(context)
        return SharedPref.read(SharedPref.USER_ID, "")
    }


}


/**
 * Simple ViewModel factory that provides the repository to the HomeViewModel.
 */
class HomeViewModelFactory(
    private val repository: IRepository<Book>,
    private val context: Context
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository, context) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}