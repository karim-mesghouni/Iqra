package com.karim_mesghouni.e_book.domain


import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Book(
    val id:String? = null,
    val name: String? = null,
    val author: String? = null,
    val genre:String? =  null,
    val category:String? = null,
    val imageUrl:String? = null,
    val published: String? = null,
    val summery:String? = null,
    val size:Int? = null,
    var isfav: Boolean? = null):Parcelable{
//    val isfav
//      get() = type == "fav"

    val launshed
        get() = published?.split("-")

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }


    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }


}



data class User(
    var name: String? = null,
    var id: String? = null,
    var imageUrl: String? = null,
    var email: String? = null,
    val favorites : List<String>?=null,
    val downloads:List<String>?=null,
    var interested:List<String>?=null)

data class BookCategory(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    var books: List<Book> = mutableListOf(),
)
data class Interested(@PropertyName("interested") private var list:MutableList<String>)