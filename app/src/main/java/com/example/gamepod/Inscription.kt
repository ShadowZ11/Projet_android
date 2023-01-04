package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

class Inscription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        val accountExist = findViewById<TextView>(R.id.already_account)
        val strAccountExist= accountExist.text
        val mSpannableString = SpannableString(strAccountExist)

        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

        accountExist.text = mSpannableString
        accountExist.setOnClickListener{
            val toConnectActivity = Intent(this, Connexion::class.java)
            startActivity(toConnectActivity)
            finish()
        }



    }
}