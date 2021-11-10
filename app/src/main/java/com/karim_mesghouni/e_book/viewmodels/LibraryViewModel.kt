package com.karim_mesghouni.e_book.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.helpers.loadBooksFromExternalStorage
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.getUserId
import com.karumi.dexter.Dexter
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: IRepository<Book>, private val context: Context) :
    ViewModel() {

    private var _favList = MutableLiveData<List<Book>?>()
    private var _downloadedList = MutableLiveData<List<Book>?>()
    private var _localDownloadedList = MutableLiveData<MutableMap<String,String>?>()


    val favList: LiveData<List<Book>?>
        get() = _favList

    val downloadedList: LiveData<List<Book>?>
        get() = _downloadedList


    val localDownloadedList: LiveData<MutableMap<String,String>?>
        get() = _localDownloadedList

    init {

        //  fetch favorites
        repository.get().addOnCompleteListener { bookList ->
            repository.getList("favorites", getUserId(context)).addOnCompleteListener { favList ->
                _favList.value = bookList.result.filter { book ->
                    favList.result?.contains(book.name!!) ?: false
                }
            }
        }

//        repository.getList("favorites", getUserId(context)).addOnCompleteListener { favList ->
//            favList.result?.forEach {
//
//            }
//        }


        // fetch downloads
        repository.get().addOnCompleteListener { bookList ->
            repository.getList("downloads",getUserId(context)).addOnCompleteListener { downloadsList ->
                _downloadedList.value = bookList.result.filter { book ->
                    downloadsList.result?.contains(book.name!!)?:false
                }
            }
        }



    }

    fun loadLocalData(){
        try {
                    viewModelScope.launch {
                        _localDownloadedList.value =
                            loadBooksFromExternalStorage(context.contentResolver)
                    }
        }catch (e:Exception){
            Firebase.crashlytics.recordException(e)
        }
    }
}

class LibraryViewModelFactory(
    private val repository: IRepository<Book>,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java))
            return LibraryViewModel(repository, context) as T
        throw IllegalAccessException("Unknown ViewModel class")
    }

}