package com.karim_mesghouni.e_book.utils

import android.content.Context

class Utils {
}

 fun getUserId(context: Context): String {
    SharedPref.init(context)
    return SharedPref.read(SharedPref.USER_ID, "")
}