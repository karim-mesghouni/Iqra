package com.karim_mesghouni.e_book.helpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import android.R
import android.content.res.Resources

import android.graphics.BitmapFactory

import android.content.Intent
import android.net.Uri


object NotificationBuilder {

    private const val NOTIFICATION_ID: Int = 9000
    private const val PENDING_INTENT_ID: Int = 9001
    private const val NOTIFICATION_CHANNEL_ID: String = "notification_channel_name"
    @SuppressLint("StaticFieldLeak")
    //private lateinit var notificationCompat : NotificationCompat.Builder
     lateinit var notificationManager : NotificationManager
    private var isNotificationNew : Boolean = true


    fun showNotification(context: Context?, title: String?, body: String?,progress: Int) {
        if (!isNotificationNew) {
            updateNotification(context,title, body,progress);
            return;
        }

        notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /**
         * Create a channel for notification
         */
        createNotificationChannel()

        /**
         * show notification
         */
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context,title,body).build())

        isNotificationNew = false
    }

    private fun updateNotification(context: Context?,title: String?, body: String?,progress:Int) {
        val notificationCompat = buildNotification(context,title,body)
        notificationCompat.setProgress(100,progress,false)
        notificationCompat.setOnlyAlertOnce(true)
        notificationManager.notify(NOTIFICATION_ID, notificationCompat.build())
    }

    // Download complete
    fun downloadCompleted(context: Context?,title: String?, body: String?,uri: Uri){
         val notify = buildNotification(context,title,body).apply {
             setContentIntent(contentIntent(context, uri))
             setOnlyAlertOnce(true)
             setSmallIcon(R.drawable.stat_sys_download_done)
         }
        notificationManager.notify(NOTIFICATION_ID, notify.build())
    }


    // Build notification
    private fun buildNotification(context: Context?,title:String?,body: String?): NotificationCompat.Builder {
         val notificationCompat =  NotificationCompat.Builder(context!!, NOTIFICATION_CHANNEL_ID).apply {
            setGroup(NotificationCompat.CATEGORY_REMINDER)
            setGroupSummary(true)
            setSmallIcon(R.drawable.stat_sys_download)
            setContentTitle(title)
            setContentText(body)
            setStyle( NotificationCompat.BigTextStyle().bigText(body))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX

        }
        return notificationCompat
    }



    private fun contentIntent(context: Context?,uri:Uri): PendingIntent? {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            type = "application/pdf"
        }
        val intent = Intent.createChooser(shareIntent,"Choose the application to share.")
        return PendingIntent.getActivity(
            context,
            PENDING_INTENT_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun largeIcon(context: Context,icon:Int): Bitmap? {
        val res: Resources = context.resources
        return BitmapFactory.decodeResource(res,icon)
    }





//    // share notification
//     fun shareNotification(context: Context, title: String?, body: String?) {
//        val stringBuilder = StringBuilder()
//        stringBuilder.append(title)
//        stringBuilder.append("\n")
//        stringBuilder.append(body)
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "text/plain"
//        intent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
//        context.startActivity(
//            Intent.createChooser(
//                intent,
//                "Choose the app you want to share on it"
//            )
//        )
//    }

    // Create a channel for notification
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "getString(R.string.channel_name)"
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

//    fun clearNotifications(context: Context) {
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancelAll()
//    }


}