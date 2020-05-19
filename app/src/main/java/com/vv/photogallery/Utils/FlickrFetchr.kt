package com.example.photogallerykotlinversion.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallerykotlinversion.API.FlickrApi
import com.example.photogallerykotlinversion.API.FlickrResponse
import com.example.photogallerykotlinversion.API.PhotoInterceptor
import com.example.photogallerykotlinversion.API.PhotoResponse
import com.example.photogallerykotlinversion.Constants.FETCH
import com.example.photogallerykotlinversion.Constants.GalleryItem
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 class FlickrFetchr {

        private val flickrApi: FlickrApi

        init {

            val client = OkHttpClient.Builder()
                .addInterceptor(PhotoInterceptor())
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            flickrApi = retrofit.create(FlickrApi::class.java)
        }

     fun fetchPhotosRequest():Call<FlickrResponse>{
         return flickrApi.fetchPhotos()
     }

     fun fetchPhotos():LiveData<List<GalleryItem>>{
         return fetchPhotoMetaData(fetchPhotosRequest())
     }

     fun searchPhotosRequest(query: String):Call<FlickrResponse>{
         return flickrApi.searchPhotos(query)
     }

     fun searchPhotos(query:String):LiveData<List<GalleryItem>>{
         return fetchPhotoMetaData(searchPhotosRequest(query))
     }


        private fun fetchPhotoMetaData(flickrRequest:Call<FlickrResponse>):LiveData<List<GalleryItem>>{
            val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()

            flickrRequest.enqueue(object : Callback<FlickrResponse> {

                override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                    Log.e(FETCH, "Failed to fetch photos", t)
                }

                override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                    Log.d(FETCH, "Response received")
                    val flickrResponse: FlickrResponse? = response.body()
                    val photoResponse: PhotoResponse? = flickrResponse?.photos
                    var galleryItems: List<GalleryItem> = photoResponse?.galleryItems
                        ?: mutableListOf()
                    galleryItems = galleryItems.filterNot {
                        it.url.isBlank()
                    }
                    responseLiveData.value = galleryItems
                }
            })

            return responseLiveData
        }
     @WorkerThread
     fun fetchPhoto(url: String): Bitmap?{
         val response: Response<ResponseBody> = flickrApi.fetchUrlBytes(url).execute()
         val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
         Log.i(FETCH,"Decoded bitmap = $bitmap from Response = $response")
         return bitmap
     }
    }
