package com.karim_mesghouni.e_book.viewmodels

//class OverviewViewModel(private val bookId:String,private val repository: IRepository<Book>) : ViewModel() {
//
//    private val _book = MutableLiveData<Book>()
//
//    val book:LiveData<Book>
//      get() = _book
//
//    init {
//        _book.value = repository.get(bookId)
//    }
//
//}
//
///**
// * Simple ViewModel factory that provides the bookId and repository to the OverviewViewModel.
// */
//class OverviewViewModelFactory(private val bookId:String,private val repository: IRepository<Book>)
//    :ViewModelProvider.Factory{
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
//            return OverviewViewModel(bookId, repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}