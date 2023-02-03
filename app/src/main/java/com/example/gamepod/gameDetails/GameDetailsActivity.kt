package com.example.gamepod.gameDetails

import GameDetailsFragment
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.gamepod.R
import com.example.gamepod.search.SearchFragment

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        val nextFragment = GameDetailsFragment()
        val bundle = Bundle()
        bundle.putInt("idGame", intent.getIntExtra("idGame", 0))
        nextFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, nextFragment).commit()
    }

}