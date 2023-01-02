package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

class Connexion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        val strForgotPassword = forgotPassword.text
        val mSpannableString = SpannableString(strForgotPassword)

        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

        forgotPassword.text = mSpannableString

    }
}