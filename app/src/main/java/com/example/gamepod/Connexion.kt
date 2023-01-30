package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Connexion : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)

        val connectButton = findViewById<Button>(R.id.connect_button)
        connectButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Connexion réussie
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Echec de la connexion
                        Toast.makeText(baseContext, "Echec de la connexion",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        val strForgotPassword = forgotPassword.text
        val mSpannableString = SpannableString(strForgotPassword)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        forgotPassword.text = mSpannableString

        val createAccount = findViewById<Button>(R.id.create_account)
        createAccount.setOnClickListener {
            val createNewAccount = Intent(this, Inscription::class.java)
            startActivity(createNewAccount)
            finish()
        }
    }
}
