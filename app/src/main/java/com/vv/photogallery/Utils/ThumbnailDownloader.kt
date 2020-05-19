package com.example.photogallerykotlinversion.Utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.photogallerykotlinversion.Constants.MESSAGE_DOWNLOAD
import com.example.photogallerykotlinversion.Constants.THUMB
import java.util.concurrent.ConcurrentHashMap

class ThumbnailDownloader<in T>(private val responseHandler: Handler,private val onThumbnailDownloaded:(T,Bitmap) -> Unit):HandlerThread(THUMB){
    private var hasQuit = false
    private lateinit var requestHandler:Handler
    private val requestMap = ConcurrentHashMap<T,String>()
    private val flickrFetchr = FlickrFetchr()

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        requestHandler = object :Handler(){
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    val target = msg.obj as T
                    Log.i(THUMB,"Got a request for URL : ${requestMap[target]}")
                    handleRequest(target)
                }
            }
        }
    }

    val fragmentLifeCycleObserver:LifecycleObserver = object :LifecycleObserver{

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun setup(){
            Log.i(THUMB,"Starting background thread")
            start()
            looper
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun tearDown(){
            Log.i(THUMB,"Destroying background thread")
            quit()
        }
    }

    val viewLifecycleObserver: LifecycleObserver =
        object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun clearQueue() {
                Log.i(THUMB, "Clearing all requests from queue")
                requestHandler.removeMessages(MESSAGE_DOWNLOAD)
                requestMap.clear()
            }
        }

    override fun quit(): Boolean {
        hasQuit = true
        return super.quit()
    }

    fun queueThumbnail(target:T,url:String){
        Log.i(THUMB,"Got a URL: $url")
        requestMap[target] = url
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget()
    }

    private fun handleRequest(target: T){
        val url = requestMap[target]?:return
        val bitmap = flickrFetchr.fetchPhoto(url)?:return

        responseHandler.post(Runnable {
            if (requestMap[target] != url || hasQuit){
                return@Runnable
            }

            requestMap.remove(target)
            onThumbnailDownloaded(target,bitmap)
        })
    }
}