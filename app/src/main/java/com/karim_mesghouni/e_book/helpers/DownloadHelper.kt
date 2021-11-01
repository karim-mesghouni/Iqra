package com.karim_mesghouni.e_book.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.net.*
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.karim_mesghouni.e_book.R
import java.io.File

fun saveFile(context: Context?,fileName:String): File {
    // get internal storage path
    val path = context?.filesDir
    return File.createTempFile(fileName,"pdf",path)
}

@SuppressLint("StringFormatMatches")
fun downloadFile(ref:StorageReference?, fileName:String, context: Context){
    // check network connection

    if (!checkNetwork(context)) {
        Toast.makeText(context, R.string.check_your_connection,LENGTH_SHORT).show()
        return
    }

    // get file path that saved in the book
    val file = ref?.child("books/$fileName.pdf")?.downloadUrl


    // get file path that have been created in storage
    val filePath = saveFile(context ,fileName)


}

// check network connection
fun checkNetwork(context: Context?):Boolean{
    val connectivityService = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityService.activeNetwork
    return network != null
}

fun getReference(path:String): StorageReference {

    return Firebase.storage.reference.child(path)
}

fun download(uri:Uri){

}


// download the file
//    file?.getFile(filePath)?.addOnSuccessListener {
//        /**
//         * file downloading completed
//         * show notification
//         */
//
//        NotificationBuilder.downloadCompleted(context,
//             context.getString(R.string.notification_title),
//                    context.getString(R.string.notification_description_completed,fileName.replaceFirstChar { it.uppercase() }),
//                            filePath.toUri())
//
//        //Toast.makeText(context, R.string.download_completed,LENGTH_SHORT).show()
//    }?.addOnFailureListener{
//        /**
//         * file downloading failed
//         * show toast
//         */
//        NotificationBuilder.notificationManager.cancelAll()
//        Toast.makeText(context,R.string.download_failed,LENGTH_SHORT).show()
//    }?.addOnProgressListener {
//        // calculate progress percentage
//         val progressPercentage = (100.0 * it.bytesTransferred / it.totalByteCount)
//        // show actual progress
//        val progress = progressPercentage.toInt()
//        NotificationBuilder.showNotification(context,
//            context.getString(R.string.notification_title),
//                    context.getString(R.string.notification_description_download,fileName),
//                         progress)
//    }

