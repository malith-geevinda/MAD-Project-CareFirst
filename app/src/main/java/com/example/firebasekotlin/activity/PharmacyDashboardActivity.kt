package com.example.firebasekotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.*
import com.google.android.material.navigation.NavigationView

class PharmacyDashboardActivity : AppCompatActivity() {

    private lateinit var AddDrug: Button
    private lateinit var SearchDrug: Button
    private lateinit var Profile: Button
    private lateinit var ViewPayment: Button
    private lateinit var ViewDelivery: Button
    private lateinit var imageButton6: Button

    lateinit var toggle: ActionBarDrawerToggle


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_dashboard)


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



        ViewPayment = findViewById<Button>(R.id.btnAddDelivery)
        AddDrug = findViewById<Button>(R.id.btnAddDrug)
        SearchDrug = findViewById<Button>(R.id.btnViewDrug)
        Profile = findViewById<Button>(R.id.btnProfile)
        ViewDelivery = findViewById<Button>(R.id.btnViewDelivery)
// imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
// imageButton6 = findViewById<ImageButton>(R.id.imageButton6)

        AddDrug.setOnClickListener {
            val intent = Intent(this, DrugInsertionActivity::class.java)
            startActivity(intent)
        }

        SearchDrug.setOnClickListener {
            val intent = Intent(this, DrugFetchingActivity::class.java)
            startActivity(intent)
        }

        Profile.setOnClickListener {
            val intent = Intent(this, ViewPharmacyProfileActivity::class.java)
            startActivity(intent)
        }

        ViewPayment.setOnClickListener {
            val intent = Intent(this, PaymentFetchingActivity::class.java)
            startActivity(intent)
        }


        ViewDelivery.setOnClickListener {
            val intent = Intent(this,DeliveryFetchingActivity::class.java)
            startActivity(intent)
        }

// imageButton2.setOnClickListener {
// val intent = Intent(this, ViewCustomerProfileActivity::class.java)
// startActivity(intent)
// }
//
// imageButton6.setOnClickListener {
// val intent = Intent(this, DrugFetchingActivity::class.java)
// startActivity(intent)
// }

    }


    //sidebar
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