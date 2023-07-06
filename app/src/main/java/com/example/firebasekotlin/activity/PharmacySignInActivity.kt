package com.example.firebasekotlin.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.firebasekotlin.R


import com.google.android.material.textfield.TextInputLayout

import com.google.firebase.auth.FirebaseAuth

class PharmacySignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_sign_in)

        auth = FirebaseAuth.getInstance()


        val signInEmail: EditText = findViewById(R.id.signInPhEmail)
        val signInPassword: EditText = findViewById(R.id.signInPhPassword)
        val signInPasswordLayout: TextInputLayout = findViewById(R.id.siginInPhPasswordLayout)
        val signInBtn: Button = findViewById(R.id.btnsignInPh)
        val signInProgressbar: ProgressBar = findViewById(R.id.signInProgressbar)

        val signUpText: TextView = findViewById(R.id.signUpTextPh)

        signUpText.setOnClickListener {
            val intent = Intent(this, PharmacySignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            signInProgressbar.visibility = View.VISIBLE
            signInPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val phEmail = signInEmail.text.toString()
            val phPassword = signInPassword.text.toString()

            if (phEmail.isEmpty() || phPassword.isEmpty()) {
                if (phEmail.isEmpty()) {
                    signInEmail.error = "Enter email address"
                }
                if (phPassword.isEmpty()) {
                    signInEmail.error = "Enter password"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }

                signInProgressbar.visibility = View.GONE
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            } else if (!phEmail.matches(emailPattern.toRegex())) {
                signInProgressbar.visibility = View.GONE
                signInEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            } else if (phPassword.length < 6) {
                signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signInProgressbar.visibility = View.GONE
                signInPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT)
                    .show()

            } else {
                auth.signInWithEmailAndPassword(phEmail, phPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, PharmacyDashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong, please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                        signInProgressbar.visibility = View.GONE
                    }
                }
            }
        }


    }
}