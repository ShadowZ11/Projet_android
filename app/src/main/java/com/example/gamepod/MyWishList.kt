package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView

class MyWishList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wish_list)

        val quitWishList = findViewById<ImageView>(R.id.quit_wishlist)
        val heartEmpty = findViewById<ImageView>(R.id.empty_list_image)


        quitWishList.setOnClickListener{
            finish()
        }

    }
}