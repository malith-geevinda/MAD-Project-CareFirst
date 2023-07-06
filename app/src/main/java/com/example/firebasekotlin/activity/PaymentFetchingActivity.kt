package com.example.firebasekotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Adapter.PaymentAdapter
import com.example.firebasekotlin.Models.PaymentModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class PaymentFetchingActivity : AppCompatActivity() {
    private lateinit var paymentRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var paymentList: ArrayList<PaymentModel>
    private lateinit var dbRef: DatabaseReference
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_fetching)
        paymentRecyclerView = findViewById(R.id.tvPayment)
        paymentRecyclerView.layoutManager = LinearLayoutManager(this)
        paymentRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        paymentList = arrayListOf<PaymentModel>()
        dbRef = FirebaseDatabase.getInstance().getReference("payments")
        getCustomersData()

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
    private fun getCustomersData(){
        paymentRecyclerView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                paymentList.clear()
                if (snapshot.exists()){
                    for (PaymentSnap in snapshot.children){
                        val PaymentsData = PaymentSnap.getValue(PaymentModel::class.java)
                        paymentList.add(PaymentsData!!)
                    }
                    val mAdapter = PaymentAdapter(paymentList)
                    paymentRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : PaymentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@PaymentFetchingActivity, PaymentDetailsActivity::class.java)

//put extras
                            intent.putExtra("PaymentId", paymentList[position].PaymentId)
                            intent.putExtra("CardNumber", paymentList[position].CardNumber)
                            intent.putExtra("CardHolderName", paymentList[position].CardHolderName)
                            intent.putExtra("ExpireMonth", paymentList[position].ExpireMonth)
                            intent.putExtra("ExpireYear", paymentList[position].ExpireYear)
                            intent.putExtra("Cvv", paymentList[position].Cvv)
                            startActivity(intent)
                        }

                    })

                    paymentRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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