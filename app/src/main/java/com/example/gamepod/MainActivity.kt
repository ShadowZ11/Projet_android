package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.myLikes.MyLikesActivity
import com.example.gamepod.myWishList.MyWishListActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val recyclerView = findViewById<RecyclerView>(R.id.list_game)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val games: MutableList<GamePreview> = mutableListOf()

        /*val games = listOf(
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30"),
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30")
        )*/


        val myLikes = findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = findViewById<ImageView>(R.id.to_my_wish_list)
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular_home)

        myLikes.setOnClickListener{
            val toMyLikes = Intent(this, MyLikesActivity::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(this, MyWishListActivity::class.java)
            startActivity(toMyWishList)
        }

        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = withContext(Dispatchers.IO) {
                    Request.getTopRelease()
                }
                val obj = request.response.pages[0]
                var valueInt = 0
                for (ids in obj.items){
                    if (valueInt > 10){
                        break
                    }
                    try {
                        Log.v("id", ids.toString())
                        val getGames = withContext(Dispatchers.IO){
                            Request.getGameById(ids.appid)
                        }
                        games.add(GamePreview(getGames.name, getGames.description, getGames.price.toString()))
                        valueInt += 1
                    }catch (e: Exception){
                        Toast.makeText(this@MainActivity, "Impossible de récupérer les infos du jeu: " + ids.appid.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                //val convertedObject: JsonObject = Gson().fromJson(request.toString(), JsonObject::class.java)
                val adapter = ListGameAdapter(games)
                progressBar.visibility = View.INVISIBLE
                recyclerView.adapter = adapter

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Impossible de récupérer les top jeux, veuillez réessayer", Toast.LENGTH_LONG).show()
                e.message?.let { Log.e("Erreur requête", it) }
            }

        }

    }

}