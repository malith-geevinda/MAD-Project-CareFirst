package com.example.firebasekotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.Models.PaymentModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentInsertionActivity : AppCompatActivity() {
    private lateinit var etCardNumber : EditText
    private lateinit var etCardHolderName : EditText
    private lateinit var etExpireMonth : EditText
    private lateinit var etExpireYear : EditText
    private lateinit var etCvv : EditText
    private lateinit var btnSavePaymentData : Button
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_insertion)

        etCardNumber = findViewById(R.id.etCardNumber)
        etCardHolderName = findViewById(R.id.etCardHolderName)
        etExpireMonth = findViewById(R.id.etExpireMonth)
        etExpireYear = findViewById(R.id.etExpireYear)
        etCvv = findViewById(R.id.etCvv)
        btnSavePaymentData = findViewById(R.id.btnSavePayment)
        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        btnSavePaymentData.setOnClickListener {

            savePaymentData()
        }

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){


                R.id.nav_home -> goToHome(Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show())
                R.id.nav_message -> goToSearchItem(Toast.makeText(applicationContext,"Clicked Search Item", Toast.LENGTH_SHORT).show())
                R.id.nav_sync -> goToAddItem(Toast.makeText(applicationContext,"Clicked Add Item", Toast.LENGTH_SHORT).show())
                R.id.nav_trash -> goToAddPayment(Toast.makeText(applicationContext,"Clicked Add Payment", Toast.LENGTH_SHORT).show())
                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext,"Clicked Logout", Toast.LENGTH_SHORT).show()
                R.id.nav_delivery -> goToDelivery(Toast.makeText(applicationContext,"Clicked Add Delivery", Toast.LENGTH_SHORT).show())
                R.id.nav_share -> Toast.makeText(applicationContext,"Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.nav_rate -> Toast.makeText(applicationContext,"Clicked rate us", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
    private fun savePaymentData(){

//getting values
        val CardNumber = etCardNumber.text.toString()
        val CardHolderName = etCardHolderName.text.toString()
        val ExpireMonth = etExpireMonth.text.toString()
        val ExpireYear = etExpireYear.text.toString()
        val Cvv = etCvv.text.toString()

        if (CardNumber .isEmpty()){
            etCardNumber .error ="Please enter Card Number"
        }

        if (CardHolderName.isEmpty()){
            etCardHolderName.error ="Please enter Card Holder Name"
        }

        if (ExpireMonth.isEmpty()){
            etExpireMonth.error ="Please enter Expire Month"
        }

        if (ExpireYear.isEmpty()){
            etExpireYear.error ="Please enter Expire Year"
        }

        if (Cvv.isEmpty()){
            etCvv.error ="Please enter Expire Year"
        }

        val PaymentId = dbRef.push().key!!

        val Payment = PaymentModel(PaymentId,CardNumber,CardHolderName,ExpireMonth,ExpireYear,Cvv)

        dbRef.child(PaymentId).setValue(Payment)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etCardNumber.text.clear()
                etCardHolderName.text.clear()
                etExpireMonth.text.clear()
                etExpireYear.text.clear()
                etCvv.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToHome(show: Unit) {
        var intent = Intent(this, CustomerDashboardActivity::class.java)
        startActivity(intent)
    }

    private fun goToSearchItem(show: Unit) {
        var intent = Intent(this,DrugFetchingActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddItem(show: Unit) {
        var intent = Intent(this,DrugInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddPayment(show: Unit) {
        var intent = Intent(this,PaymentInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun goToDelivery(show: Unit) {
        var intent = Intent(this,DeliveryInsertionActivity::class.java)
        startActivity(intent)
    }
}