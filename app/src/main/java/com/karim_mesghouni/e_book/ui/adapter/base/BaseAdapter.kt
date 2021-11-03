package com.karim_mesghouni.e_book.ui.adapter.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory

abstract class BaseAdapter<T>(
    var itemLayoutRes: Int? = null,
    var items: List<T>?,

) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {

                val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes!!, parent, false)
                return  BaseViewHolderImp(view)
    }



    override fun getItemCount(): Int {

        return items?.size!!

    }

    override fun getItemViewType(position: Int): Int {
        return itemLayoutRes!!
    }



    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items?.get(position)!!, position)
    }

    inner class BaseViewHolderImp(itemView: View) : BaseViewHolder<T>(itemView) {
        override fun bind(item: T, position: Int) {
            this@BaseAdapter.bind(itemView, item, position, this)
        }
    }

    abstract fun bind(itemView: View, item: T, position: Int, viewHolder: BaseViewHolderImp)
}