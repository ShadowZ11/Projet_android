package com.example.gamepod.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gamepod.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.timerTask

class ForgotPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_forgot_password, container, false)
        auth = FirebaseAuth.getInstance()

        val titleTextHeader = view.findViewById<TextView>(R.id.title)
        val descriptionHeader = view.findViewById<TextView>(R.id.description_forgot_password)
        val emailField = view.findViewById<EditText>(R.id.email)
        val sendButton = view.findViewById<Button>(R.id.button_reset)
        val problemFieldEmail = view.findViewById<ImageView>(R.id.problem_field_email)
        problemFieldEmail.visibility = View.INVISIBLE

        titleTextHeader.text = titleTextHeader.text.substring(0, titleTextHeader.text.length - 2)

        sendButton.setOnClickListener {
            if (emailField.text.isNotEmpty()) {
                auth.sendPasswordResetEmail(emailField.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Email de réinitialisation envoyé avec succès!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Erreur lors de l'envoi de l'email de réinitialisation: " + task.exception,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                problemFieldEmail.visibility = View.VISIBLE

                emailField.setBackgroundResource(R.drawable.custom_wrong_input_field)

                Timer().schedule(timerTask {
                    problemFieldEmail.visibility = View.INVISIBLE
                    emailField.setBackgroundResource(R.drawable.custom_edit_text)}, 7000)
                Toast.makeText(requireContext(), "Le champ email est vide", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return view
    }
}
