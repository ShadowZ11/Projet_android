package com.example.gamepod.connexion

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.gamepod.R

class ConnexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConnexionFragment.newInstance()).commit()
    }
}
