import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gamepod.R
import com.example.gamepod.connexion.ConnexionActivity
import com.example.gamepod.main.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.concurrent.timerTask

class InscriptionFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var progressDialog: AlertDialog

    companion object {
        fun newInstance() = InscriptionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_inscription, container, false)

        auth = FirebaseAuth.getInstance()

        emailField = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)
        usernameField = view.findViewById(R.id.username_field)

        val warningEmailField = view.findViewById<ImageView>(R.id.problem_field_email)
        val warningUsernameField = view.findViewById<ImageView>(R.id.problem_field_username)
        val warningPasswordField = view.findViewById<ImageView>(R.id.problem_field_password)
        val warningVPasswordField = view.findViewById<ImageView>(R.id.problem_field_verify_password)

        warningEmailField.visibility = View.INVISIBLE
        warningUsernameField.visibility = View.INVISIBLE
        warningPasswordField.visibility = View.INVISIBLE
        warningVPasswordField.visibility = View.INVISIBLE

        val accountExist = view.findViewById<TextView>(R.id.already_account)
        val strAccountExist = accountExist.text
        val mSpannableString = SpannableString(strAccountExist)

        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

        accountExist.text = mSpannableString
        accountExist.setOnClickListener {
            val toConnectActivity = Intent(activity, ConnexionActivity::class.java)
            startActivity(toConnectActivity)
        }

        val registerBtn = view.findViewById<Button>(R.id.submit_button)
        registerBtn.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (TextUtils.isEmpty(email)) {
                warningEmailField.visibility = View.VISIBLE
                emailField.setBackgroundResource(R.drawable.custom_wrong_input_field)
                Timer().schedule(timerTask {
                    warningEmailField.visibility = View.INVISIBLE
                    emailField.setBackgroundResource(R.drawable.custom_edit_text)}, 7000)
                Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                warningPasswordField.visibility = View.VISIBLE
                passwordField.setBackgroundResource(R.drawable.custom_wrong_input_field)
                Timer().schedule(timerTask {
                    warningPasswordField.visibility = View.INVISIBLE
                    passwordField.setBackgroundResource(R.drawable.custom_edit_text)}, 7000)
                Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                warningPasswordField.visibility = View.VISIBLE
                passwordField.setBackgroundResource(R.drawable.custom_wrong_input_field)
                Timer().schedule(timerTask {
                    warningPasswordField.visibility = View.INVISIBLE
                    passwordField.setBackgroundResource(R.drawable.custom_edit_text)}, 7000)
                Toast.makeText(
                    context,
                    "Password too short, enter minimum 6 characters!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            progressDialog = AlertDialog.Builder(activity)
                .setTitle("Registering...")
                .setView(R.layout.progress_dialog)
                .setCancelable(false)
                .create()
            progressDialog.show()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()
                        val collectionReference = db.collection("users")
                        val user = auth.currentUser

                        val userData = hashMapOf(
                            "userId" to (user?.uid ?: String),
                            "username" to usernameField.text.toString()
                        )

                        collectionReference
                            .add(userData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                context, "Registration Successful. Welcome ${user?.email}",
                                Toast.LENGTH_SHORT
                            ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    context,
                                    "Registration failed. ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@addOnFailureListener
                            }

                        val intent = Intent(activity, ConnexionActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Registration failed. ${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressDialog.dismiss()
                })
        }
        return  view
    }
}

