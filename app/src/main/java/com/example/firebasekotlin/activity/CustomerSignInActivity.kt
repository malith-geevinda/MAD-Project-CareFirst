package com.example.firebasekotlin.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class CustomerSignInActivity : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sign_in)

        auth = FirebaseAuth.getInstance()


        val signInEmail : EditText = findViewById(R.id.signInCusEmail)
        val signInPassword : EditText = findViewById(R.id.signInCusPassword)
        val signInPasswordLayout : TextInputLayout = findViewById(R.id.siginInCusPasswordLayout)
        val signInBtn : Button = findViewById(R.id.btnsignInCus)
        val signInProgressbar : ProgressBar = findViewById(R.id.signInProgressbar)

        val signUpText : TextView = findViewById(R.id.signUpTextCus)

        signUpText.setOnClickListener{
            val intent = Intent(this, CustomerSignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener{
            signInProgressbar.visibility = View.VISIBLE
            signInPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val cusEmail = signInEmail.text.toString()
            val cusPassword = signInPassword.text.toString()

            if(cusEmail.isEmpty() || cusPassword.isEmpty()){
                if(cusEmail.isEmpty()){
                    signInEmail.error = "Enter email address"
                }
                if(cusPassword.isEmpty()){
                    signInEmail.error = "Enter password"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }

                signInProgressbar.visibility = View.GONE
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }else if(!cusEmail.matches(emailPattern.toRegex())){
                signInProgressbar.visibility = View.GONE
                signInEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if(cusPassword.length<6){
                signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signInProgressbar.visibility = View.GONE
                signInPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT).show()

            }else{
                auth.signInWithEmailAndPassword(cusEmail, cusPassword).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, CustomerDashboardActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show()
                        signInProgressbar.visibility = View.GONE
                    }
                }
            }
        }


    }
}