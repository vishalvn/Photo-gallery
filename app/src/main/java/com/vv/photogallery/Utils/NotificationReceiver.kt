package com.example.photogallerykotlinversion.Utils

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.photogallerykotlinversion.Constants.NOTIFICATION
import com.example.photogallerykotlinversion.Constants.NOTIFY
import com.example.photogallerykotlinversion.Constants.REQUEST_CODE

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(NOTIFY,"Received Result: $resultCode")
        if (resultCode != Activity.RESULT_OK){
        }
        val requestCode = intent.getIntExtra(REQUEST_CODE,0)
        val notification : Notification = intent.getParcelableExtra(NOTIFICATION)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(requestCode,notification)
    }
}