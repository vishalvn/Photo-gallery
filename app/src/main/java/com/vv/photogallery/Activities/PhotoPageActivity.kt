package com.example.photogallerykotlinversion.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallerykotlinversion.Fragments.PhotoPageFragment
import com.example.photogallerykotlinversion.R

class PhotoPageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null){
            val fragment = PhotoPageFragment.newInstance(intent.data)
            fm.beginTransaction()
                .add(R.id.fragmentContainer,fragment)
                .commit()
        }
    }

    companion object{
        fun newIntent(context: Context,photoPageUri:Uri):Intent{
            return Intent(context,
                PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
        }
    }
}
