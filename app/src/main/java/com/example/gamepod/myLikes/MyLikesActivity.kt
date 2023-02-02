package com.example.gamepod.myLikes

import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gamepod.R

class MyLikesActivity : AppCompatActivity(), MyLikesFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MyLikesFragment.newInstance()).commit()
    }

    override fun onQuitLikes() {
        finish()
    }
}