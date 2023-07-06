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
import com.example.firebasekotlin.Models.DrugModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DrugInsertionActivity : AppCompatActivity(){

    private lateinit var etDrugName : EditText
    private lateinit var etDrugBrand : EditText
    private lateinit var etDrugPrice : EditText
    private lateinit var etQuantity : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef:DatabaseReference
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etDrugName = findViewById(R.id.etDrugName)
        etDrugBrand = findViewById(R.id.etDrugBrand)
        etDrugPrice = findViewById(R.id.etDrugPrice)
        etQuantity = findViewById(R.id.etQuantity)
        btnSaveData = findViewById(R.id.btnSave)
        dbRef =FirebaseDatabase.getInstance().getReference("Drugs")

        btnSaveData.setOnClickListener {

            saveDrugData()
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
    private fun saveDrugData(){

//getting values
        val DrugName = etDrugName.text.toString()
        val DrugBrand = etDrugBrand.text.toString()
        val DrugPrice = etDrugPrice.text.toString()
        val Quantity = etQuantity.text.toString()

        if (DrugName.isEmpty()){
            etDrugName.error ="Please enter Drug Name"
        }

        if (DrugBrand.isEmpty()){
            etDrugBrand.error ="Please enter Drug Brand"
        }

        if (DrugPrice.isEmpty()){
            etDrugPrice.error ="Please enter Drug Price"
        }

        if (Quantity.isEmpty()){
            etQuantity.error ="Please enter Quantity"
        }

        val DrugId = dbRef.push().key!!

        val Drug = DrugModel(DrugId,DrugName,DrugBrand,DrugPrice,Quantity)

        dbRef.child(DrugId).setValue(Drug)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG).show()

                etDrugName.text.clear()
                etDrugBrand.text.clear()
                etDrugPrice.text.clear()
                etQuantity.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
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
        var intent = Intent(this, PaymentInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun goToDelivery(show: Unit) {
        var intent = Intent(this,DeliveryInsertionActivity::class.java)
        startActivity(intent)
    }
}