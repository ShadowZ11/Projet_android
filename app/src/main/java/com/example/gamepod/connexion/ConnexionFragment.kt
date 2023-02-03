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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gamepod.R
import com.example.gamepod.forgotPassword.ForgotPasswordActivity
import com.example.gamepod.inscription.InscriptionActivity
import com.example.gamepod.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Timer
import kotlin.concurrent.timerTask

class Connect{
    companion object{
        @JvmStatic lateinit var uuidUser: String
        @JvmStatic lateinit var email: String
        @JvmStatic lateinit var userId: String
        @JvmStatic lateinit var username: String
    }

}
class ConnexionFragment : Fragment() {

    lateinit var auth: FirebaseAuth



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
        val problemFieldEmail = view.findViewById<ImageView>(R.id.problem_field_email)
        val problemFieldPassword = view.findViewById<ImageView>(R.id.problem_field_password)

        problemFieldEmail.visibility = View.INVISIBLE
        problemFieldPassword.visibility = View.INVISIBLE

        val connectButton = view.findViewById<Button>(R.id.connect_button)
        connectButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Connexion réussie
                        val db = FirebaseFirestore.getInstance()
                        val collectionReference = db.collection("users")
                        val user = auth.currentUser
                        if (user != null) {
                            Connect.uuidUser = user.uid
                            Connect.email = user.email.toString()
                        }else{
                            Toast.makeText(requireContext(), "Echec de la connexion",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }
                        collectionReference.get().addOnSuccessListener { result ->
                            for (document in result) {
                                if (Connect.uuidUser == document.data["userId"]){
                                    Connect.userId = document.data["userId"].toString()
                                    Connect.username = document.data["username"].toString()
                                    break
                                }
                            }
                        }.addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Echec de la connexion",
                                Toast.LENGTH_SHORT).show()
                            return@addOnFailureListener
                        }
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        problemFieldEmail.visibility = View.VISIBLE
                        problemFieldPassword.visibility = View.VISIBLE

                        problemFieldEmail.setBackgroundResource(R.drawable.custom_wrong_input_field)
                        problemFieldPassword.setBackgroundResource(R.drawable.custom_wrong_input_field)

                        Timer().schedule(timerTask {
                            problemFieldEmail.visibility = View.INVISIBLE
                            problemFieldPassword.visibility = View.INVISIBLE

                            problemFieldEmail.setBackgroundResource(R.drawable.custom_edit_text)
                            problemFieldPassword.setBackgroundResource(R.drawable.custom_edit_text)
                        }, 7000)

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

        forgotPassword.setOnClickListener{
            val newForgotPassword = Intent(activity, ForgotPasswordActivity::class.java)
            startActivity(newForgotPassword)
            activity?.finish()
        }

        val createAccount = view.findViewById<Button>(R.id.create_account)
        createAccount.setOnClickListener {
            val createNewAccount = Intent(activity, InscriptionActivity::class.java)
            startActivity(createNewAccount)
            activity?.finish()
        }
    }
}
