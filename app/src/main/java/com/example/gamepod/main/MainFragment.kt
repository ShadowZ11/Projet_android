package com.example.gamepod.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.*
import com.example.gamepod.myLikes.MyLikesActivity
import com.example.gamepod.myWishList.MyWishListActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.home, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.list_game)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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

        val myLikes = rootView.findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = rootView.findViewById<ImageView>(R.id.to_my_wish_list)

        myLikes.setOnClickListener{
            val toMyLikes = Intent(requireContext(), MyLikesActivity::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(requireContext(), MyWishListActivity::class.java)
            startActivity(toMyWishList)
        }

        return rootView
    }
}
