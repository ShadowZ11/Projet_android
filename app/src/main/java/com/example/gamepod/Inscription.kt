package com.example.gamepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import android.text.TextUtils
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Inscription : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        auth = FirebaseAuth.getInstance()

        emailField = findViewById(R.id.email_field)
        passwordField = findViewById(R.id.password_field)


        val accountExist = findViewById<TextView>(R.id.already_account)
        val strAccountExist = accountExist.text
        val mSpannableString = SpannableString(strAccountExist)

        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

        accountExist.text = mSpannableString
        accountExist.setOnClickListener {
            val toConnectActivity = Intent(this, ConnexionActivity::class.java)
            startActivity(toConnectActivity)
            finish()
        }

        val registerBtn = findViewById<Button>(R.id.submit_button)
        registerBtn.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressDialog = AlertDialog.Builder(this)
                .setTitle("Registering...")
                .setView(R.layout.progress_dialog)
                .setCancelable(false)
                .create()
            progressDialog.show()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Registration Successful. Welcome ${user?.email}",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed. ${task.exception}", Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.dismiss()
                })
        }
    }
}
