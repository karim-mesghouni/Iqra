package com.karim_mesghouni.e_book.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.repository.IRepository

class SearchViewModel(private val repository: IRepository<Book>) : ViewModel() {
    private val _BookList: MutableLiveData<List<Book>> by lazy {
        MutableLiveData<List<Book>>().also {
            it.value = emptyList<Book>()
        }
    }
    val bookList:LiveData<List<Book>>
      get() = _BookList
    init {
        repository.get().addOnCompleteListener {bookList ->
            _BookList.value = bookList.result
        }
    }
}

class SearchViewModelFactory(
    private val repository: IRepository<Book>,
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java))
            return SearchViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}