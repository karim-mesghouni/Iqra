package com.karim_mesghouni.e_book.ui.adapter

import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.domain.BookCategory
import com.karim_mesghouni.e_book.ui.adapter.base.BaseAdapter
import com.karim_mesghouni.e_book.ui.adapter.base.BaseViewHolder


class BookCategoryAdapter(
    var categories: MutableList<BookCategory> = mutableListOf(), private val listener: (Book) -> Unit, private val more: (BookCategory) -> Unit
) : BaseAdapter<BookCategory>(
    itemLayoutRes = R.layout.main_rv_item,
    items = categories,
) {


    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()

    private val viewPool = RecyclerView.RecycledViewPool()



    private fun getSectionID(position: Int): String {

        return categories?.get(position)?.id!!
    }



    override fun onViewRecycled(holder: BaseViewHolder<BookCategory>) {
        super.onViewRecycled(holder)


        //save horizontal scroll state
        val key = getSectionID(holder.layoutPosition)

            scrollStates[key] =
                holder.itemView.findViewById<RecyclerView>(R.id.books_rv).layoutManager?.onSaveInstanceState()

   }

    override fun bind(

        itemView: View,
        item: BookCategory,
        position: Int,
        viewHolder: BaseViewHolderImp
    ) {

        if (position == 0)
            itemView.findViewById<View>(R.id.rectangle).visibility = View.GONE
        itemView.findViewById<TextView>(R.id.book_category)?.text = item.title
        itemView.findViewById<ImageView>(R.id.more_book).setOnClickListener{
            more(item)

        }
        val layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        layoutManager.initialPrefetchItemCount = 4

        val titledSectionRecycler = itemView.findViewById<RecyclerView>(R.id.books_rv)
        titledSectionRecycler?.run {
            this.setRecycledViewPool(viewPool)
            this.layoutManager = layoutManager
            this.adapter = BookAdapter(item.books,listener = listener,mLayout = R.layout.book_item)
        }

        //restore horizontal scroll state
        val key = getSectionID(viewHolder.layoutPosition)
        val state = scrollStates[key]
        if (state != null) {
            titledSectionRecycler.layoutManager?.onRestoreInstanceState(state)
        } else {
            titledSectionRecycler.layoutManager?.scrollToPosition(0)
        }
    }


//     fun setUp(
//
//         itemView: View,
//         item: BookCategory,
//         position: Int,
//         viewHolder: BaseViewHolderImp){
//
//         when(getItemViewType(position )){
//             1 ->{
//                 itemView.findViewById<TextView>(R.id.category_large)?.text = item.title
//                 itemView.findViewById<ImageView>(R.id.more_book_larg).setOnClickListener{
//                     more(item)
//                 }
//                 val layoutManager =
//                     LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//
//                 layoutManager.initialPrefetchItemCount = 4
//
//                 val titledSectionRecycler = itemView.findViewById<RecyclerView>(R.id.books_rv_large)
//
//                 titledSectionRecycler?.run {
//                     this.setRecycledViewPool(viewPool)
//                     this.layoutManager = layoutManager
//                     this.adapter = BookAdapter(item.books,listener)
//                 }
//
//                 //restore horizontal scroll state
//                 val key = getSectionID(viewHolder.layoutPosition)
//                 val state = scrollStates[key]
//                 if (state != null) {
//                     titledSectionRecycler.layoutManager?.onRestoreInstanceState(state)
//                 } else {
//                     titledSectionRecycler.layoutManager?.scrollToPosition(0)
//                 }
//             }
//             0 ->{
//                 itemView.findViewById<TextView>(R.id.book_category)?.text = item.title
//                 itemView.findViewById<ImageView>(R.id.more_book).setOnClickListener{
//                     more(item)
//                 }
//                 val layoutManager =
//                     LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//
//                 layoutManager.initialPrefetchItemCount = 4
//
//                 val titledSectionRecycler = itemView.findViewById<RecyclerView>(R.id.books_rv)
//                 titledSectionRecycler?.run {
//                     this.setRecycledViewPool(viewPool)
//                     this.layoutManager = layoutManager
//                     this.adapter = BookAdapter(item.books,listener)
//                 }
//
//                 //restore horizontal scroll state
//                 val key = getSectionID(viewHolder.layoutPosition)
//                 val state = scrollStates[key]
//                 if (state != null) {
//                     titledSectionRecycler.layoutManager?.onRestoreInstanceState(state)
//                 } else {
//                     titledSectionRecycler.layoutManager?.scrollToPosition(0)
//                 }
//             }
//         }
//     }
}


