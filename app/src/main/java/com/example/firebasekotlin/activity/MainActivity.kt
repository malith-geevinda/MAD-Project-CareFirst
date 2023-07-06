package com.example.firebasekotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.firebasekotlin.R

class MainActivity : AppCompatActivity() {

    private lateinit var pharmacy: Button
    private lateinit var customer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pharmacy = findViewById(R.id.pharmacy)
        customer = findViewById(R.id.customer)


        pharmacy.setOnClickListener {
            val intent = Intent(this, PharmacySignUpActivity::class.java)
            startActivity(intent)
        }

        customer.setOnClickListener {
            val intent = Intent(this, CustomerSignUpActivity::class.java)
            startActivity(intent)
        }

    }




}
