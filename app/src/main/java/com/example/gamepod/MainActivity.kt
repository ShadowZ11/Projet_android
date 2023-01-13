package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


        val myLikes = findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = findViewById<ImageView>(R.id.to_my_wish_list)

        myLikes.setOnClickListener{
            val toMyLikes = Intent(this, MyLikes::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(this, MyWishList::class.java)
            startActivity(toMyWishList)
        }


    }


}