package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView

class Connexion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        val strForgotPassword = forgotPassword.text
        val mSpannableString = SpannableString(strForgotPassword)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        forgotPassword.text = mSpannableString


        val connectButton = findViewById<Button>(R.id.connect_button)
        val createAccount = findViewById<Button>(R.id.create_account)


        createAccount.setOnClickListener {
            val createNewAccount = Intent(this, Inscription::class.java)
            startActivity(createNewAccount)
            finish()
        }

    }
}