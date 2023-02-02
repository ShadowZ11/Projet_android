package com.example.gamepod.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gamepod.R

class ForgotPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_forgot_password, container, false)
        val titleTextHeader = view.findViewById<TextView>(R.id.title)
        val descriptionHeader = view.findViewById<TextView>(R.id.description_forgot_password)
        val emailField = view.findViewById<EditText>(R.id.email)
        val sendButton = view.findViewById<Button>(R.id.button_reset)

        titleTextHeader.text = titleTextHeader.text.substring(0, titleTextHeader.text.length - 2)

        sendButton.setOnClickListener {
            if (emailField.text.isNotEmpty()) {
                Toast.makeText(requireContext(), "Le champ email est non-vide", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Le champ email est vide", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return view
    }
}