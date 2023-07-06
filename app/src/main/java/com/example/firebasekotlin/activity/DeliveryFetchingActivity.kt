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
import com.example.firebasekotlin.Adapter.DeliveryAdapter
import com.example.firebasekotlin.Models.DeliveryModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class DeliveryFetchingActivity : AppCompatActivity() {
    private lateinit var deliveryRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var deliveryList: ArrayList<DeliveryModel>
    private lateinit var dbRef: DatabaseReference

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_fetching)
        deliveryRecyclerView = findViewById(R.id.tvDelivery)
        deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        deliveryRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        deliveryList = arrayListOf<DeliveryModel>()
        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")
        getDeliveriesData()

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
    private fun getDeliveriesData(){
        deliveryRecyclerView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryList.clear()
                if (snapshot.exists()){
                    for (DeliverySnap in snapshot.children){
                        val DeliveriesData = DeliverySnap.getValue(DeliveryModel::class.java)
                        deliveryList.add(DeliveriesData!!)
                    }
                    val mAdapter = DeliveryAdapter(deliveryList)
                    deliveryRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : DeliveryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@DeliveryFetchingActivity, DeliveryDetailsActivity::class.java)

//put extras
                            intent.putExtra("DeliveryId", deliveryList[position].DeliveryId)
                            intent.putExtra("DeliveryAddress", deliveryList[position].DeliveryAddress)
                            intent.putExtra("CustomerContactNumber", deliveryList[position].CustomerContactNumber)
                            intent.putExtra("DeliveryDate", deliveryList[position].DeliveryDate)
                            intent.putExtra("DeliveryMan", deliveryList[position].DeliveryMan)
                            startActivity(intent)
                        }

                    })

                    deliveryRecyclerView.visibility = View.VISIBLE
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
        var intent = Intent(this, DeliveryInsertionActivity::class.java)
        startActivity(intent)
    }
}