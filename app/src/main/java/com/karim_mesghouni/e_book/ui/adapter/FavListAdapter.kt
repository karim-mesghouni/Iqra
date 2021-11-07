package com.karim_mesghouni.e_book.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.ui.adapter.base.BaseListAdapter

class FavListAdapter():BaseListAdapter<Book>(R.layout.book_item_fav,DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    override fun bind(itemView: View, item: Book, position: Int, viewHolder: BaseViewHolderImp) {
       itemView.run {
           findViewById<ImageView>(R.id.fav_book_image).load(item.imageUrl)
           findViewById<TextView>(R.id.fav_book_title).text = item.name
           findViewById<TextView>(R.id.fav_book_author).text = item.author
       }
    }
}