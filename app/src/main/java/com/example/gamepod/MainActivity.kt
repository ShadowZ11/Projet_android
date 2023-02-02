package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.myLikes.MyLikesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val recyclerView = findViewById<RecyclerView>(R.id.list_game)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val games = listOf(
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30"),
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30")
        )
        val adapter = ListGameAdapter(games)
        recyclerView.adapter = adapter

        val myLikes = findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = findViewById<ImageView>(R.id.to_my_wish_list)

        myLikes.setOnClickListener{
            val toMyLikes = Intent(this, MyLikesActivity::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(this, MyWishList::class.java)
            startActivity(toMyWishList)
        }


    }


}