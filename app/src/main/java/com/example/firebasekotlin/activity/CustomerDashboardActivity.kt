package com.example.firebasekotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView


private lateinit var AddPayment: Button
private lateinit var ViewPayment: Button
private lateinit var AddDelivery: Button
private lateinit var ViewDelivery: Button
private lateinit var Profile: Button

class CustomerDashboardActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_dashboard)


        AddPayment = findViewById<Button>(R.id.btnAddDrug)
        ViewPayment = findViewById<Button>(R.id.btnViewDrug)
        AddDelivery = findViewById<Button>(R.id.btnAddDelivery)
        ViewDelivery = findViewById<Button>(R.id.btnViewDelivery)
        Profile = findViewById<Button>(R.id.btnProfile)

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


        AddPayment.setOnClickListener {
            val intent = Intent(this, DrugFetchingActivity::class.java)
            startActivity(intent)
        }

        ViewPayment.setOnClickListener {
            val intent = Intent(this, PaymentInsertionActivity::class.java)
            startActivity(intent)
        }

        AddDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryInsertionActivity::class.java)
            startActivity(intent)
        }

        ViewDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryFetchingActivity::class.java)
            startActivity(intent)
        }
        Profile.setOnClickListener {
            val intent = Intent(this, ViewCustomerProfileActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToHome(show: Unit) {
        var intent = Intent(this,CustomerDashboardActivity::class.java)
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