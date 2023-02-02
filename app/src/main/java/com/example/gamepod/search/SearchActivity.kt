package com.example.gamepod.search

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.gamepod.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SearchFragment.newInstance()).commit()
    }

}