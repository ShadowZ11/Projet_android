package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val titleTextHeader = findViewById<TextView>(R.id.title)
        val descriptionHeader = findViewById<TextView>(R.id.description_forgot_password)
        val emailField = findViewById<EditText>(R.id.email)
        val sendButton = findViewById<Button>(R.id.button_reset)

        titleTextHeader.text = titleTextHeader.text.substring(0, titleTextHeader.text.length-2)


        sendButton.setOnClickListener{

            if (emailField.text.isNotEmpty()){
                Toast.makeText(this, "Le champ email est non-vide", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Le champ email est vide", Toast.LENGTH_LONG).show()
            }

        }

    }
}