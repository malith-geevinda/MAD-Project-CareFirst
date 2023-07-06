package com.example.firebasekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.Models.CustomerModel
import com.example.firebasekotlin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class CustomerSignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


//Assign IDs to variables
        val signUpCusName : EditText = findViewById(R.id.signUpCusName)
        val signUpCusEmail : EditText = findViewById(R.id.signUpCusEmail)
        val signUpCusPhone : EditText = findViewById(R.id.signUpCusPhone)
        val signUpCusPassword : EditText = findViewById(R.id.signUpCusPassword)
        val signUpCusConfiPassword : EditText = findViewById(R.id.signUpCusConfiPassword)
        val siginUpCusPasswordLayout : TextInputLayout = findViewById(R.id.siginUpCusPasswordLayout)
        val siginUpCusConfiPasswordLayout : TextInputLayout = findViewById(R.id.siginUpCusConfiPasswordLayout)
        val signUpCusBtn : Button = findViewById(R.id.btnsignUpCus)
        val signUpCusProgressbar : ProgressBar = findViewById(R.id.signUpProgressbar)

        val signInText : TextView = findViewById(R.id.signInTextCus)

        signInText.setOnClickListener{
            val intent = Intent(this,CustomerSignInActivity::class.java)
            startActivity(intent)
        }

        signUpCusBtn.setOnClickListener {
            val cusName = signUpCusName.text.toString()
            val cusEmail = signUpCusEmail.text.toString()
            val cusPhone = signUpCusPhone.text.toString()
            val cusPassword = signUpCusPassword.text.toString()
            val cusConfirPassword = signUpCusConfiPassword.text.toString()

            signUpCusProgressbar.visibility = View.VISIBLE
            siginUpCusPasswordLayout.isPasswordVisibilityToggleEnabled = true
            siginUpCusConfiPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if(cusName.isEmpty() || cusEmail.isEmpty() || cusPhone.isEmpty() || cusPassword.isEmpty() || cusConfirPassword.isEmpty()){

                if(cusName.isEmpty()){
                    signUpCusName.error = "Enter your name"
                }

                if(cusEmail.isEmpty()){
                    signUpCusEmail.error = "Enter your email"
                }

                if(cusPhone.isEmpty()){
                    signUpCusPhone.error = "Enter your mobile number"
                }

                if(cusPassword.isEmpty()){
                    siginUpCusPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpCusPassword.error = "Enter your password"
                }

                if(cusConfirPassword.isEmpty()){
                    siginUpCusConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpCusConfiPassword.error = "Confirm your password"

                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                signUpCusProgressbar.visibility = View.GONE

            }else if(!cusEmail.matches(emailPattern.toRegex())){
                signUpCusProgressbar.visibility = View.GONE
                signUpCusEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if(cusPhone.length != 10){
                signUpCusProgressbar.visibility = View.GONE
                signUpCusPhone.error = "Enter valid mobile number"
                Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show()

            }else if(cusPassword.length<6){
                siginUpCusPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpCusProgressbar.visibility = View.GONE
                signUpCusPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT).show()

            }else if(cusPassword != cusConfirPassword){
                siginUpCusConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpCusProgressbar.visibility = View.GONE
                signUpCusConfiPassword.error = "password not matched"
                Toast.makeText(this, "password not matched", Toast.LENGTH_SHORT).show()

            }else{
                auth.createUserWithEmailAndPassword(cusEmail, cusPassword).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("Customers").child(auth.currentUser!!.uid)
                        val Customers : CustomerModel = CustomerModel(cusName, cusEmail,cusPhone, auth.currentUser!!.uid)

                        databaseRef.setValue(Customers).addOnCompleteListener{
                            if(it.isSuccessful){
                                val intent = Intent(this,CustomerSignUpActivity::class.java)
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