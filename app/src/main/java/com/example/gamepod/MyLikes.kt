package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyLikes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_likes)


        val quitLikes = findViewById<ImageView>(R.id.quit_wishlist)
        val heartEmpty = findViewById<ImageView>(R.id.empty_list_image)

        //TODO : si cache vide afficher emptylikes, si cache pas vide afficher list_likes avec le cache,
        // et en parallèle lancer la requete au back :
        // si la requete est passée alors actuliser les données,
        // sinon afficher un pop up toast pour dire derniere actualisation il y a x temps

        quitLikes.setOnClickListener{
            finish()
        }

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


    }
}