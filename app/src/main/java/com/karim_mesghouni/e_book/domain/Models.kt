package com.karim_mesghouni.e_book.domain


import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Book(
    val id:String? = null,
    val name: String? = null,
    val author: String? = null,
    val genre:String? =  null,
    val category:String? = null,
    val imageUrl:String? = null,
    val url:String? = null,
    val published: String? = null,
    var summery: String? = null,
    val size: Int? = null,
    var isFav: Boolean? = false):Parcelable{


    val launched
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
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )


    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (name != other.name) return false
        if (author != other.author) return false
        if (genre != other.genre) return false
        if (category != other.category) return false
        if (imageUrl != other.imageUrl) return false
        if (url != other.url) return false
        if (published != other.published) return false
        if (summery != other.summery) return false
        if (size != other.size) return false
        if (isFav != other.isFav) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (published?.hashCode() ?: 0)
        result = 31 * result + (summery?.hashCode() ?: 0)
        result = 31 * result + (size ?: 0)
        result = 31 * result + (isFav?.hashCode() ?: 0)
        return result
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
    val favorites : List<String>?= emptyList(),
    val downloads:List<String>?= emptyList(),
    var interested:List<String>?=null)

data class BookCategory(
    val id: String = UUID.randomUUID().toString(),
    val title: String ,
    var books: List<Book> = mutableListOf(),
)

