package com.karim_mesghouni.e_book.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karim_mesghouni.e_book.ui.adapter.BookAdapter
import com.karim_mesghouni.e_book.ui.adapter.FavListAdapter


@BindingAdapter("fav")
fun setfav(image:ImageView,isFav:Boolean){
    if (isFav)
        image.setImageResource(R.drawable.ic_bookmark_black)
    else
        image.setImageResource(R.drawable.ic_bookmark)
}
@BindingAdapter("imageUrl")
fun setImage(image: ImageView,url:String){
    image.load(url)
}

@BindingAdapter("listData")
fun bind(recyclerView: RecyclerView,data:List<Book>?){
    Log.d("BindingAdapter",data.toString())
   val adapter = recyclerView.adapter as FavListAdapter
   adapter.submitList(data)

}


