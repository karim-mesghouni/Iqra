package com.karim_mesghouni.e_book.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.karim_mesghouni.e_book.R


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


