package com.example.photogallerykotlinversion.API

import com.example.photogallerykotlinversion.Constants.GalleryItem
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}