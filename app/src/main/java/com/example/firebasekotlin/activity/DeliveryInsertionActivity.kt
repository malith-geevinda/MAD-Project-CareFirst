package com.example.firebasekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.Models.DeliveryModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeliveryInsertionActivity : AppCompatActivity() {
    private lateinit var etDeliveryAddress : EditText
    private lateinit var etCustomerContactNumber: EditText
    private lateinit var etDeliveryDate : EditText
    private lateinit var etDeliveryMan : EditText
    private lateinit var btnSaveDeliveryData : Button

    private lateinit var dbRef: DatabaseReference
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_insertion)

        etDeliveryAddress = findViewById(R.id.etDeliveryAddress)
        etCustomerContactNumber = findViewById(R.id.etCustomerContactNumber)
        etDeliveryDate = findViewById(R.id.etDeliveryDate)
        etDeliveryMan = findViewById(R.id.etDeliveryMan)
        btnSaveDeliveryData = findViewById(R.id.btnSaveDelivery)
        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")

        btnSaveDeliveryData.setOnClickListener {

            saveDeliveryData()

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
    }
    private fun saveDeliveryData(){

//getting values
        val DeliveryAddress = etDeliveryAddress.text.toString()
        val CustomerContactNumber = etCustomerContactNumber.text.toString()
        val DeliveryDate = etDeliveryDate.text.toString()
        val DeliveryMan = etDeliveryMan .text.toString()

        if (DeliveryAddress .isEmpty()){
            etDeliveryAddress .error ="Please enter DeliveryAddress"
        }

        if (CustomerContactNumber.isEmpty()){
            etCustomerContactNumber.error ="Please enter CustomerContactNumber"
        }

        if (DeliveryDate.isEmpty()){
            etDeliveryDate.error ="Please enter DeliveryDate"
        }

        if (DeliveryMan.isEmpty()){
            etDeliveryMan.error ="Please enter DeliveryMan"
        }

        val DeliveryId = dbRef.push().key!!

        val Delivery = DeliveryModel(DeliveryId,DeliveryAddress,CustomerContactNumber,DeliveryDate,DeliveryMan)

        dbRef.child(DeliveryId).setValue(Delivery)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etDeliveryAddress.text.clear()
                etCustomerContactNumber.text.clear()
                etDeliveryDate.text.clear()
                etDeliveryMan.text.clear()

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
        var intent = Intent(this, DrugFetchingActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddItem(show: Unit) {
        var intent = Intent(this, DrugInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddPayment(show: Unit) {
        var intent = Intent(this, PaymentInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun goToDelivery(show: Unit) {
        var intent = Intent(this,DeliveryInsertionActivity::class.java)
        startActivity(intent)
    }
}