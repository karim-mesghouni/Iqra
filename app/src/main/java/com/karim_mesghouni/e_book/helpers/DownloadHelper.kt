package com.karim_mesghouni.e_book.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.FragmentActivity
import com.google.firebase.storage.StorageReference
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.Book
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("StringFormatMatches")
fun downloadFile(ref: StorageReference?, fileName: String, context: Context) {
    // check network connection

    if (!checkNetwork(context)) {
        Toast.makeText(context, R.string.check_your_connection, LENGTH_SHORT).show()
        return
    }

    // get file path that saved in the book
    val file = ref?.child("books/$fileName.pdf")?.downloadUrl


}

// check network connection
fun checkNetwork(context: Context?): Boolean {
    val connectivityService =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityService.activeNetwork
    return network != null
}


fun download(activity: FragmentActivity, book: Book) {
    val manager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val uri: Uri =
        Uri.parse(book.url)
    val request = DownloadManager.Request(uri)

    request.setDestinationInExternalPublicDir("/iqraDownloads", "${book.name}.pdf")

    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    val enqueue = manager.enqueue(request)
}

private suspend fun loadPhotosFromExternalStorage(resolver: ContentResolver): List<String> {
    return withContext(Dispatchers.IO) {
        val collection = sdk29AndUp {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } ?: MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME
        )

        val selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + " =? "
        val args = arrayOf("karim")
        val photos = mutableListOf<String>()

        resolver.query(
            collection,
            projection,
            selection,
            args,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)


            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri("external"),
                    id
                )
                if (displayName.contains("pdf"))
                    photos.add(contentUri.toString())
            }
            photos
        } ?: listOf()
    }


}

inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        onSdk29()
    } else null
}







