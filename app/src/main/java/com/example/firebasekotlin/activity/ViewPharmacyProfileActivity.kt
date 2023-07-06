package com.example.firebasekotlin.activity

import DeletePharmacyProfileActivity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.Models.CustomerModel
import com.example.firebasekotlin.Models.Pharmacy
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewPharmacyProfileActivity : AppCompatActivity() {
    private lateinit var viewProfileName: TextView
    private lateinit var viewProfileEmail: TextView
    private lateinit var viewProfileMobileNumber: TextView


    private lateinit var btnUpdateProfile : Button
    private lateinit var btnDeleteProfile : Button

    lateinit var toggle: ActionBarDrawerToggle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.firebasekotlin.R.layout.activity_view_pharmacy_profile)

        viewProfileName = findViewById(com.example.firebasekotlin.R.id.viewProfileName)
        viewProfileEmail = findViewById(com.example.firebasekotlin.R.id.viewProfileEmail)
        viewProfileMobileNumber = findViewById(com.example.firebasekotlin.R.id.viewProfileMobileNumber)
// viewProfileHeaderEmail = findViewById(com.example.firebasekotlin.R.id.viewProfileHeaderEmail)
// viewProfileHeaderName = findViewById(com.example.firebasekotlin.R.id.viewProfileHeaderName)

//Assign btn IDs to variables
        btnUpdateProfile = findViewById(com.example.firebasekotlin.R.id.btnUpdateProfile)
        btnDeleteProfile = findViewById(com.example.firebasekotlin.R.id.btnDeleteProfile)

//ImageViews as buttons
// val homeIcon = findViewById<ImageView>(R.id.ivHome)
// val timetableIcon = findViewById<ImageView>(R.id.ivTimetable)
// val resultsIcon = findViewById<ImageView>(R.id.ivResults)

        getCustomerData()

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


//Onclick listners for image views
// homeIcon.setOnClickListener {
// val intent = Intent(this, HomeActivity::class.java) //change activity
// startActivity(intent)
// }
//
// timetableIcon.setOnClickListener{
// val intent = Intent(this, TeacherDetailsActivity::class.java) //change activity
// startActivity(intent)
// }
//
// resultsIcon.setOnClickListener {
// val intent = Intent(this, ShashikaMainActivity::class.java) //change activity
// startActivity(intent)
// }


        btnUpdateProfile.setOnClickListener {
// val intent = Intent(this, UpdateProfileActivity::class.java) //change activity
// startActivity(intent)

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val userRef = FirebaseDatabase.getInstance().getReference("pharmacy/$userId")

// openUpdateDialog(
// intent.getStringExtra("userId").toString()
// )

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pharmacy = snapshot.getValue(Pharmacy::class.java)
                    openUpdateDialog(pharmacy?.uid.toString(), pharmacy?.phName.toString(), pharmacy?.phEmail.toString(), pharmacy?.phPhone.toString())
                }

                override fun onCancelled(error: DatabaseError) {
// Log.w(TAG, "Failed to read user data.", error.toException())
                }
            })
        }

        btnDeleteProfile.setOnClickListener {
            val intent = Intent(this, DeletePharmacyProfileActivity::class.java) //change activity
            startActivity(intent)
        }


    }

    private fun getCustomerData(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = FirebaseDatabase.getInstance().getReference("pharmacy/$userId")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val customer = snapshot.getValue(CustomerModel::class.java)
                viewProfileName.text = customer?.cusName
                viewProfileEmail.text = customer?.cusEmail
                viewProfileMobileNumber.text = customer?.cusPhone
// viewProfileHeaderName.text = customer?.cusName
// viewProfileHeaderEmail.text = customer?.cusEmail
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read user data.", error.toException())
            }

        })
    }

    private fun openUpdateDialog(
        userId: String,
        profName: String,
        profEmail: String,
        profPhoneNo: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(com.example.firebasekotlin.R.layout.activity_edit_customer_profile, null)

        mDialog.setView(mDialogView)

        val etProfName = mDialogView.findViewById<EditText>(com.example.firebasekotlin.R.id.editCustomerName)
        val etProfEmail = mDialogView.findViewById<EditText>(com.example.firebasekotlin.R.id.editCustomerEmail)
        val etProfPhoneNo = mDialogView.findViewById<EditText>(com.example.firebasekotlin.R.id.editCustomerMobileNumber)



        etProfName.setText(profName)
        etProfEmail.setText(profEmail)
        etProfPhoneNo.setText(profPhoneNo)

        mDialog.setTitle("Updating $etProfName record!")

        val alertDialog = mDialog.create()
        alertDialog.show()

        val btnUpdate = mDialogView.findViewById<Button>(com.example.firebasekotlin.R.id.btnUpdateEditProfile)

        btnUpdate.setOnClickListener{
            updateProfileData(
                userId,
                etProfName.text.toString(),
                etProfEmail.text.toString(),
                etProfPhoneNo.text.toString(),
            )



//set updated data to textviews
            viewProfileName.text = etProfName.text.toString()
            viewProfileEmail.text = etProfEmail.text.toString()
            viewProfileMobileNumber.text = etProfPhoneNo.text.toString()

            alertDialog.dismiss()

            val intent = Intent(this, ViewPharmacyProfileActivity::class.java) //change activity
            startActivity(intent)
        }
    }

    private fun updateProfileData(
        userId: String,
        name: String,
        email: String,
        phoneNo: String,
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("pharmacy").child(userId)
        val profInfo = CustomerModel(name, email, phoneNo, userId)
        dbRef.setValue(profInfo)

        val user = FirebaseAuth.getInstance().currentUser
        user?.updateEmail(email)?.addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(applicationContext, "Profile Not Updated", Toast.LENGTH_LONG).show()
            }
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