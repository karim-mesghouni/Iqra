package com.karim_mesghouni.e_book.helpers

import android.Manifest
import android.content.*
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.karim_mesghouni.e_book.domain.Book
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import android.content.Intent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase


suspend fun loadBooksFromExternalStorage(contentResolver:ContentResolver):MutableMap<String,String> {
    return withContext(Dispatchers.IO) {
        val collection = sdk29AndUp {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } ?: MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME)

        val selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + " =? "
        val args = arrayOf("iqraDownloads")
        val photos =  mutableMapOf<String,String>()

        contentResolver.query(
            collection,
            projection,
            selection,
            args,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)


            while(cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri("external"),
                    id
                )
                if (displayName.contains("pdf"))
                    photos[displayName.split(".")[0]] = contentUri.toString()
            }
            photos
        } ?: mutableMapOf()
    }


}



fun open(uri: String?,context: Context) {
   // val booksPath =  context.getExternalFilesDir("iqraDownloads")
    //val book = File(booksPath,name)
   // val uri = FileProvider.getUriForFile(context,"com.karim_mesghouni.e_book",book)
    if (uri.isNullOrEmpty()){
        return
    }
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM,Uri.parse(uri))
        //flags = Intent.FLAG_ACTIVITY_NEW_TASK
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        type = "application/pdf"


    }


    try {
        context.startActivity(Intent.createChooser(shareIntent, "Choose the application to share."))
    }catch (ex:Exception){
        Firebase.crashlytics.recordException(ex)
    }

}