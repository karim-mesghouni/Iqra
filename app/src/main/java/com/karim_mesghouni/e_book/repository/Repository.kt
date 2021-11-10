package com.karim_mesghouni.e_book.repository

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.getUserId
import java.util.Collections.emptyList

class Repository<Entity >(
    private val entityClass:Class<Entity>,
    private val collection: String,
    private val context: Context
) : IRepository<Entity> {

    /** get an Instance from fireStore**/
    private val db = Firebase.firestore

    /** add new user **/
    override fun add(user: User): Task<Void> {

        return db.collection(Constants.USER_COLLECTION).document(user.id.toString()).set(user)
    }
    /** get book or get user **/
    override fun get(id: String): Task<Entity?> {
    //    var entity:Entity? = null
         return db.collection(collection).document(id).get().continueWith {
               if (it.isSuccessful){
                   return@continueWith it.result.toObject(entityClass)
               }else{
                   return@continueWith null
               }
        }

    }

    /**
     * get interests or downloads or favorites list
     */
    override fun getList(value: String,userId:String): Task<List<String>?> {
//        var list:List<String>? = null
//        db.collection("users").document(id).get().addOnSuccessListener { it ->
//
//            list = it.get("interested") as List<String>
//
//        }
        var array: List<String>?
        return db.collection(Constants.USER_COLLECTION).document(userId).get().continueWith {
            if (it.isSuccessful) {
                array = it.result.get(value) as List<String>?
                return@continueWith array
            } else {
                return@continueWith emptyList()
            }
        }
       // db.collection("users").whereArrayContains("favorites",value)
        //This query returns every user document where the favorites field is an array that contains value. If the array has multiple instances of the value you query on, the document is included in the results only once.

    }


    /** get books by category  **/
    override fun getByCategory(category: String): Task<List<Entity>> {
        val books: MutableList<Entity> = mutableListOf()

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



    /** get all books**/
    override fun get(): Task<List<Book>> {
        val books: MutableList<Book> = mutableListOf()
        return db.collection(Constants.BOOK_COLLECTION).get().continueWith {
            if (it.isSuccessful){
                for (doc in it.result)
                    books.add(doc.toObject(Book::class.java))
                return@continueWith books
            }else{
                books
            }
        }
    }

    /**
     * @field is the name of the array that you want to change
     * @item is the array item that you want to change
     */
    /** add book to fav or download list **/
    override fun add(item: String?, field: String?): Task<Void> {
        return db.collection(Constants.USER_COLLECTION).document(getUserId(context)).update(field!!,FieldValue.arrayUnion(item))

    }
    /** remove book to fav or download list **/
    override fun remove(item: String?, field: String?): Task<Void>{
        return db.collection(Constants.USER_COLLECTION).document(getUserId(context)).update(field!!,FieldValue.arrayRemove(item))

    }
}


interface IRepository<Entity > {
    fun add(user: User): Task<Void>
    fun add(item: String?,field:String?): Task<Void>
    fun remove(item:String?,field:String?):Task<Void>
    fun get(id: String): Task<Entity?>
    fun get(): Task<List<Book>>
    fun getByCategory(category: String): Task<List<Entity>>
    fun getList(value:String,userId: String): Task<List<String>?>
//    fun delete(id: String)
}



