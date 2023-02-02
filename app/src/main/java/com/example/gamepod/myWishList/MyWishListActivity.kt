package com.example.gamepod.myWishList

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.gamepod.R
import com.example.gamepod.myLikes.MyLikesFragment

class MyWishListActivity : AppCompatActivity(), MyWishListFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MyWishListFragment.newInstance()).commit()
    }

    override fun onQuitLikes() {
        finish()
    }
}