package com.karim_mesghouni.e_book.repository

import android.util.Log
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Repository<Entity >(
    private val entityClass:Class<Entity>,
    private val collection: String
) : IRepository<Entity> {

    /** get an Instance from fireStore**/
    private val db = Firebase.firestore

    /** add new user **/
    override fun add(user: User): Task<Void> {

        return db.collection(Constants.USER_COLLECTION).document(user.id.toString()).set(user)
    }
    /** get book or get user **/
    override fun get(id: String):Entity?{
        var entity:Entity? = null
         db.collection(collection).document(id).get().addOnSuccessListener { documentSnapshot ->
               entity = documentSnapshot.toObject(entityClass)
        }
        return entity
    }

    override fun getList(id: String): List<String> {
        var list:List<String>? = null
        db.collection("users").document(id).get().addOnSuccessListener { it ->

            list = it.get("interested") as List<String>

        }
        return list!!
    }

    /** get books by category  **/
    override fun getByCategory(category: String): Task<List<Entity>> {
        val books: MutableList<Entity> = mutableListOf()
        var book = Book()
        return db.collection(Constants.BOOK_COLLECTION).whereEqualTo("category", category).get()
            .continueWith {
                if (it.isSuccessful ) {
                    for (doc in it.result) {
                        books.add(doc.toObject(entityClass))
                    }
                    return@continueWith books
                } else {
                    return@continueWith emptyList<Entity>()
                }
            }
    }
//
//        get()
//            .addOnSuccessListener { documents ->
//
//                for (doc in documents) {
//                    book = doc.toObject(Book::class.java)
//                    books.add(book)
//                }
//                Log.d("Tad",books[0].name!!)
//            }


  //  }



    /** get all books**/
    override fun get(): List<Book> {
        val books: MutableList<Book> = mutableListOf()
        db.collection(Constants.BOOK_COLLECTION).get().addOnSuccessListener {
           for (book in it)
               books.add(book.toObject())
        }
        return books
    }
    /** add book to fav or download list **/
    override fun add(id: String, field: String): Task<Void> {
        return db.collection(collection).document("user ID").update(field,FieldValue.arrayUnion(id))
    }
    /** remove book to fav or download list **/
    override fun remove(id: String, field: String): Task<Void>{
        return db.collection(collection).document("user ID").update(field,FieldValue.arrayRemove(id))
    }
}


interface IRepository<Entity > {
    fun add(user: User): Task<Void>
    fun add(id: String,field:String): Task<Void>
    fun remove(id:String,field:String):Task<Void>
    fun get(id: String): Entity?
    fun get(): List<Book>
    fun getByCategory(category: String): Task<List<Entity>>
    fun getList(id: String): List<String>
//    fun delete(id: String)
}



