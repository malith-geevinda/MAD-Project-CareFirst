package com.example.firebasekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.Models.Pharmacy
import com.example.firebasekotlin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PharmacySignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


//Assign IDs to variables

        val signUpPhName : EditText = findViewById(R.id.signUpPhName)
        val signUpPhEmail : EditText = findViewById(R.id.signUpPhEmail)
        val signUpPhPhone : EditText = findViewById(R.id.signUpPhPhone)
        val signUpPhPassword : EditText = findViewById(R.id.signUpPhPassword)
        val signUpPhConfiPassword : EditText = findViewById(R.id.signUpPhConfiPassword)
        val siginUpPhPasswordLayout : TextInputLayout = findViewById(R.id.siginUpPhPasswordLayout)
        val siginUpPhConfiPasswordLayout : TextInputLayout = findViewById(R.id.siginUpPhConfiPasswordLayout)
        val signUpPhBtn : Button = findViewById(R.id.btnsignUpPh)
        val signUpPhProgressbar : ProgressBar = findViewById(R.id.signUpProgressbar)

        val signInText : TextView = findViewById(R.id.signInTextPh)

        signInText.setOnClickListener{
            val intent = Intent(this, PharmacySignInActivity::class.java)
            startActivity(intent)
        }

        signUpPhBtn.setOnClickListener {
            val phName = signUpPhName.text.toString()
            val phEmail = signUpPhEmail.text.toString()
            val phPhone = signUpPhPhone.text.toString()
            val phPassword = signUpPhPassword.text.toString()
            val phConfirPassword = signUpPhConfiPassword.text.toString()

            signUpPhProgressbar.visibility = View.VISIBLE
            siginUpPhPasswordLayout.isPasswordVisibilityToggleEnabled = true
            siginUpPhConfiPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if(phName.isEmpty() || phEmail.isEmpty() || phPhone.isEmpty() || phPassword.isEmpty() || phConfirPassword.isEmpty()){

                if(phName.isEmpty()){
                    signUpPhName.error = "Enter your name"
                }

                if(phEmail.isEmpty()){
                    signUpPhEmail.error = "Enter your email"
                }

                if(phPhone.isEmpty()){
                    signUpPhPhone.error = "Enter your mobile number"
                }

                if(phPassword.isEmpty()){
                    siginUpPhPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpPhPassword.error = "Enter your password"
                }

                if(phConfirPassword.isEmpty()){
                    siginUpPhConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpPhConfiPassword.error = "Confirm your password"

                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                signUpPhProgressbar.visibility = View.GONE

            }else if(!phEmail.matches(emailPattern.toRegex())){
                signUpPhProgressbar.visibility = View.GONE
                signUpPhEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if(phPhone.length != 10){
                signUpPhProgressbar.visibility = View.GONE
                signUpPhPhone.error = "Enter valid mobile number"
                Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show()

            }else if(phPassword.length<6){
                siginUpPhPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpPhProgressbar.visibility = View.GONE
                signUpPhPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT).show()

            }else if(phPassword != phConfirPassword){
                siginUpPhConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpPhProgressbar.visibility = View.GONE
                signUpPhConfiPassword.error = "password not matched"
                Toast.makeText(this, "password not matched", Toast.LENGTH_SHORT).show()

            }else{
                auth.createUserWithEmailAndPassword(phEmail, phPassword).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("pharmacy").child(auth.currentUser!!.uid)
                        val pharmacy : Pharmacy = Pharmacy(phName , phEmail,phPhone, auth.currentUser!!.uid)

                        databaseRef.setValue(pharmacy).addOnCompleteListener{
                            if(it.isSuccessful){
                                val intent = Intent(this, PharmacySignInActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}