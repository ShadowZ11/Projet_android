package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView

class MyLikes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_likes)


        val quitLikes = findViewById<ImageView>(R.id.quit_wishlist)
        val heartEmpty = findViewById<ImageView>(R.id.empty_list_image)


        quitLikes.setOnClickListener{
            finish()
        }


    }
}