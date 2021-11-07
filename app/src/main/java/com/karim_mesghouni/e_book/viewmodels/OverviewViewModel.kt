package com.karim_mesghouni.e_book.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.utils.getUserId

class OverviewViewModel(private val mBook: Book,private val context: Context, private val repository: IRepository<Book>) : ViewModel() {

    private val _book = MutableLiveData<Book>()
    private var _favList = MutableLiveData<List<String?>>()
    private var _downloadedList = MutableLiveData<List<String?>>()
    private val _isFav = MutableLiveData(false)
    private val _isDownloaded = MutableLiveData(false)

    val book: LiveData<Book>
        get() = _book

    val favList:LiveData<List<String?>>
       get() = _favList


    val isFav: LiveData<Boolean>
        get() = _isFav

    val isDownloaded: LiveData<Boolean>
        get() = _isDownloaded

    fun setFav(isFav:Boolean){
        _isFav.value = isFav
    }
    fun setDown(isDown:Boolean){
       _isDownloaded.value = isDown
    }


    init {

        repository.getList("downloads", getUserId(context)).addOnCompleteListener {
            _downloadedList.value = it.result
            _isDownloaded.value = _downloadedList.value?.contains(mBook.name)
        }
        repository.getList("favorites",getUserId(context)).addOnCompleteListener {
           _favList.value = it.result
            Log.d(TAG,it.result.toString())
        }
        _book.value = mBook
    }




    private fun getFavorites(){
        _favList.value = getList()
        Log.d(TAG,_favList.value.toString())
    }

    private fun getList(): List<String> {
        var interests = emptyList<String>()
        repository.getList("favorites",getUserId(context)).addOnCompleteListener {
            interests = it.result ?: emptyList()
            Log.d(TAG,it.result.toString())
        }
        return interests
    }




    override fun onCleared() {
        super.onCleared()
        upDateFav()
        upDateDownloaded()
    }

    private fun upDateFav(){
        if (_favList.value?.contains(mBook.name)!! && !_isFav.value!!){
            // remove book from favorites
            repository.remove(mBook.name,"favorites").addOnCompleteListener {
                if (it.isSuccessful)
                    Log.d(TAG,"${mBook.name} removed from 'favorites'")
            }

        }else if (!_favList.value?.contains(mBook.name)!! && _isFav.value!!){
            // add book to favorites
            repository.add(mBook.name,"favorites").addOnCompleteListener{
                if (it.isSuccessful)
                    Log.d(TAG,"${mBook.name} added to 'favorites'")
            }
        }
        else
            return
    }

    private fun upDateDownloaded(){
        if (!_downloadedList.value?.contains(mBook.name)!! && _isDownloaded.value!! ){
            repository.add(mBook.name,"downloads").addOnCompleteListener{
                if (it.isSuccessful)
                    Log.d(TAG,"${mBook.name} added to 'downloads'")
            }
        }
    }

companion object{
    const val TAG = "OverView"
}

}

/**
 * Simple ViewModel factory that provides the bookId and repository to the OverviewViewModel.
 */
class OverviewViewModelFactory(private val book: Book,private val context: Context,private val repository: IRepository<Book>)
    : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(book,context, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}