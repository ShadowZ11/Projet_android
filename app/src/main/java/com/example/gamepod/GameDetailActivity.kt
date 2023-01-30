package com.example.gamepod

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class GameDetailActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)
        val game = intent.getParcelableExtra("game", GamePreview::class.java)
        val imageView = findViewById<ImageView>(R.id.image_description_game)
        val titleView = findViewById<TextView>(R.id.game_details_title)
        val descriptionView = findViewById<TextView>(R.id.description_path)
        //val priceView = findViewById<TextView>(R.id.game_details_price)
        if (game != null) {
            titleView.text = game.title
        }
        //descriptionView.text = game.description
        //priceView.text = game.price
    }
}