package com.example.firebasekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Adapter.DrugAdapter
import com.example.firebasekotlin.Models.DrugModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DrugFetchingActivity : AppCompatActivity() {
    private lateinit var drugRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var drugList: ArrayList<DrugModel>
    private lateinit var dbRef:DatabaseReference

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        drugRecyclerView = findViewById(R.id.tvDrug)
        drugRecyclerView.layoutManager = LinearLayoutManager(this)
        drugRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        drugList = arrayListOf<DrugModel>()
        dbRef =FirebaseDatabase.getInstance().getReference("Drugs")
        getDrugsData()

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
    private fun getDrugsData(){
        drugRecyclerView.visibility = View.GONE
        tvLoadingData.visibility=View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Drugs")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                drugList.clear()
                if (snapshot.exists()){
                    for (DrugSnap in snapshot.children){
                        val DrugData = DrugSnap.getValue(DrugModel::class.java)
                        drugList.add(DrugData!!)
                    }
                    val mAdapter = DrugAdapter(drugList)
                    drugRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : DrugAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@DrugFetchingActivity, DrugDetailsActivity::class.java)

//put extras
                            intent.putExtra("DrugId", drugList[position].DrugId)
                            intent.putExtra("DrugName", drugList[position].DrugName)
                            intent.putExtra("DrugBrand", drugList[position].DrugBrand)
                            intent.putExtra("DrugPrice", drugList[position].DrugPrice)
                            intent.putExtra("Quantity", drugList[position].Quantity)
                            startActivity(intent)
                        }

                    })

                    drugRecyclerView.visibility = View.VISIBLE
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