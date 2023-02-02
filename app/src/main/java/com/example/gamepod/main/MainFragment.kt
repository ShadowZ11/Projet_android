package com.example.gamepod.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.GamePreview
import com.example.gamepod.ListGameAdapter
import com.example.gamepod.R
import com.example.gamepod.Request
import com.example.gamepod.myLikes.MyLikesActivity
import com.example.gamepod.myLikes.MyLikesFragment
import com.example.gamepod.myWishList.MyWishListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_game)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val games: MutableList<GamePreview> = mutableListOf()

        val myLikes = view.findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = view.findViewById<ImageView>(R.id.to_my_wish_list)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular_home)

        myLikes.setOnClickListener {
            val toMyLikes = Intent(activity, MyLikesActivity::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(activity, MyWishListActivity::class.java)
            startActivity(toMyWishList)
        }

        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = withContext(Dispatchers.IO) {
                    Request.getTopRelease()
                }
                val obj = request.response.pages[0]
                var valueInt = 0
                for (ids in obj.items) {
                    if (valueInt > 10) {
                        break
                    }
                    try {
                        Log.v("id", ids.toString())
                        val getGames = withContext(Dispatchers.IO) {
                            Request.getGameById(ids.appid)
                        }
                        games.add(
                            GamePreview(
                                getGames.name,
                                getGames.description,
                                getGames.price.toString()
                            )
                        )
                        valueInt += 1
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity as MainActivity,
                            "Impossible de récupérer les infos du jeu: " + ids.appid.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val adapter = ListGameAdapter(games)
                progressBar.visibility = View.INVISIBLE
                recyclerView.adapter = adapter

            } catch (e: Exception) {
                Toast.makeText(
                    activity as MainActivity,
                    "Impossible de récupérer les top jeux, veuillez réessayer",
                    Toast.LENGTH_LONG
                ).show()
                e.message?.let { Log.e("Erreur requête", it) }
            }

        }

        return view
    }

}
