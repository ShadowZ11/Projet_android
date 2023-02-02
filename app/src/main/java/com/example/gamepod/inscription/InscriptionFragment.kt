import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gamepod.R
import com.example.gamepod.connexion.ConnexionActivity
import com.example.gamepod.main.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class InscriptionFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
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
                Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
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
                        val user = auth.currentUser
                        Toast.makeText(
                            context, "Registration Successful. Welcome ${user?.email}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(activity, MainActivity::class.java)
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

