package com.example.gamepod.connexion

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gamepod.MainActivity
import com.example.gamepod.R
import com.example.gamepod.inscription.InscriptionActivity
import com.google.firebase.auth.FirebaseAuth

class ConnexionFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = ConnexionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)

        val connectButton = view.findViewById<Button>(R.id.connect_button)
        connectButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Connexion réussie
                        val user = auth.currentUser
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        // Echec de la connexion
                        Toast.makeText(requireContext(), "Echec de la connexion",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val forgotPassword = view.findViewById<TextView>(R.id.forgot_password)
        val strForgotPassword = forgotPassword.text
        val mSpannableString = SpannableString(strForgotPassword)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        forgotPassword.text = mSpannableString

        val createAccount = view.findViewById<Button>(R.id.create_account)
        createAccount.setOnClickListener {
            val createNewAccount = Intent(activity, InscriptionActivity::class.java)
            startActivity(createNewAccount)
            activity?.finish()
        }
    }
}
