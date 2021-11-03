package com.karim_mesghouni.e_book.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.ui.adapter.base.BaseAdapter

class BookAdapter(books :List<Book> = emptyList(), private val listener: (Book) -> Unit): BaseAdapter<Book>(R.layout.book_item,books) {

    override fun bind(itemView: View, item: Book, position: Int, viewHolder: BaseViewHolderImp) {

        itemView.run {
            findViewById<TextView>(R.id.book_title).text = item.name
            findViewById<TextView>(R.id.book_author).text = item.author

            findViewById<ImageView>(R.id.book_image)?.load(item.imageUrl)

        }
        itemView.setOnClickListener {
            listener(item)
        }

    }
}

