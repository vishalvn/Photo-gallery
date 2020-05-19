package com.example.photogallerykotlinversion.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photogallerykotlinversion.Fragments.PhotoGalleryFragment
import com.example.photogallerykotlinversion.R

class PhotoGalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, PhotoGalleryFragment.newInstance())
                .commit()
        }
    }

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,
                PhotoGalleryActivity::class.java)
        }
    }
}
