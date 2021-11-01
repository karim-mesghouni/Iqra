package com.karim_mesghouni.e_book.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.utils.SharedPref

/**
 * The [ViewModel] that is associated with the [HomeFragment].
 */
class HomeViewModel(repository: IRepository<Book>, context: Context) : ViewModel(){


//  private val bookRepository  = Repository(Book::class.java,Constants.BOOK_COLLECTION)




    private val _trendingList = MutableLiveData<BookCategory>()

  val trendingList : LiveData<BookCategory>
      get() = _trendingList


  private val _newReleasesList = MutableLiveData<BookCategory>()

  val newReleasesList : LiveData<BookCategory>
    get() = _newReleasesList


  private val _forYouList = MutableLiveData<BookCategory>()

  val forYouList : LiveData<BookCategory>
    get() = _forYouList


  init {

      _trendingList.value = repository.getByCategory("").run {
          return@run BookCategory(books = this,title = "Trending")
      }

      _newReleasesList.value = repository.getByCategory("").let {
          return@let BookCategory(books = it,title = "New Releases")
      }

      /**filter all books to get book that user intrest in by { filter {arrayOfUserIntrst.contians(it.genre}}**/
//      _forYouList.value = repository.getList(getUserId(context)).run {
//          val list = this!!
//          repository.get().run {
//              return@run BookCategory(books = this.filter { list.contains(it.genre!!) },title = "For You")
//          }
//      }
//      _forYouList.value = repository.get().run {
//          val list = repository.getList(getUserId(context))
//          BookCategory(books = this.filter { list.contains(it.genre!!) },title = "For You")
//      }
  }


    private fun getUserId(context: Context):String{
        SharedPref.init(context)
        return SharedPref.read(SharedPref.USER_ID,"")
    }




}



/**
 * Simple ViewModel factory that provides the repository to the HomeViewModel.
 */
class HomeViewModelFactory(private val repository: IRepository<Book>, private val context: Context) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository,context) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}