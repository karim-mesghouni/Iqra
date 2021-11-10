package com.karim_mesghouni.e_book.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.ui.adapter.base.BaseAdapter

class BookAdapter(books :List<Book>? = emptyList(), private val mLayout:Int,private val listener: ((Book) -> Unit)?): BaseAdapter<Book>(mLayout,books) {

    override fun bind(itemView: View, item: Book, position: Int, viewHolder: BaseViewHolderImp) {
        when(mLayout){
            R.layout.book_item ->{
                itemView.run {
                    findViewById<TextView>(R.id.book_title).text = item.name
                    findViewById<TextView>(R.id.book_author).text = item.author

                    findViewById<ImageView>(R.id.book_image)?.load(item.imageUrl)

                }
            }

            R.layout.book_item_large ->{
                itemView.run {
                    findViewById<TextView>(R.id.book_title_down).text = item.name
                    findViewById<ImageView>(R.id.book_image_down)?.load(item.imageUrl)

                }
            }
        }

        itemView.setOnClickListener {
            listener?.invoke(item)
        }

    }
}

