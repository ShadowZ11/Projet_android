package com.example.gamepod

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class ConnexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connexion_container)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConnexionFragment.newInstance()).commit()
    }
}
