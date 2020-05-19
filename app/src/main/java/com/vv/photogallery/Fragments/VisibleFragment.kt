package com.example.photogallerykotlinversion.Fragments

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.photogallerykotlinversion.Constants.ACTION_SHOW_NOTIFICATION
import com.example.photogallerykotlinversion.Constants.PERM_PRIVATE
import com.example.photogallerykotlinversion.Constants.VISIBILE_FRAGMENT

abstract class VisibleFragment:Fragment() {
    private val onShowNotification = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
           Log.i(VISIBILE_FRAGMENT,"Cancelling notification")
            resultCode = Activity.RESULT_CANCELED
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ACTION_SHOW_NOTIFICATION)
        requireActivity().registerReceiver(onShowNotification,filter, PERM_PRIVATE,null)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(onShowNotification)
    }
}