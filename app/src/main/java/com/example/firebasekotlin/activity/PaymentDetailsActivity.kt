package com.example.firebasekotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.Models.PaymentModel
import com.example.firebasekotlin.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase

class PaymentDetailsActivity : AppCompatActivity() {
    private lateinit var tvPaymentId: TextView
    private lateinit var tvCardNumber: TextView
    private lateinit var tvCardHolderName: TextView
    private lateinit var tvExpireMonth: TextView
    private lateinit var tvExpireYear: TextView
    private lateinit var tvCvv: TextView
    private lateinit var btnUpdatePayment: Button
    private lateinit var btnDeletePayment: Button
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        initView()
        setValuesToViews()

        btnUpdatePayment.setOnClickListener {
            openPaymentUpdateDialog(
                intent.getStringExtra("PaymentId").toString(),
                intent.getStringExtra("CardNumber").toString(),


                )
        }

        btnDeletePayment.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("PaymentId").toString()
            )
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

    private fun initView() {
        tvPaymentId = findViewById(R.id.tvPaymentId)
        tvCardNumber = findViewById(R.id.tvCardNumber)
        tvCardHolderName = findViewById(R.id.tvCardHolderName)
        tvExpireMonth = findViewById(R.id.tvExpireMonth)
        tvExpireYear = findViewById(R.id.tvExpireYear)
        tvCvv = findViewById(R.id.tvCvv)

        btnUpdatePayment = findViewById(R.id.btnUpdatePayment)
        btnDeletePayment = findViewById(R.id.btnDeletePayment)
    }

    private fun setValuesToViews() {
        tvPaymentId.text = intent.getStringExtra("PaymentId")
        tvCardNumber.text = intent.getStringExtra("CardNumber")
        tvCardHolderName.text = intent.getStringExtra("CardHolderName")
        tvExpireMonth.text = intent.getStringExtra("ExpireMonth")
        tvExpireYear.text = intent.getStringExtra("ExpireYear")
        tvCvv.text = intent.getStringExtra("Cvv")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Payment data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, PaymentFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openPaymentUpdateDialog(
        PaymentId: String,
        CardNumber: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_payment_dialog, null)

        mDialog.setView(mDialogView)

        val etCardNumber = mDialogView.findViewById<EditText>(R.id.etCardNumber)
        val etCardHolderName= mDialogView.findViewById<EditText>(R.id.etCardHolderName)
        val etExpireMonth = mDialogView.findViewById<EditText>(R.id.etExpireMonth)
        val etExpireYear = mDialogView.findViewById<EditText>(R.id.etExpireYear)
        val etCvv = mDialogView.findViewById<EditText>(R.id.etCvv)

        val btnUpdatePayment = mDialogView.findViewById<Button>(R.id.btnUpdatePayment)

        etCardNumber.setText(intent.getStringExtra("CardNumber").toString())
        etCardHolderName.setText(intent.getStringExtra("CardHolderName").toString())
        etExpireMonth.setText(intent.getStringExtra("ExpireMonth").toString())
        etExpireYear.setText(intent.getStringExtra("ExpireYear").toString())
        etCvv.setText(intent.getStringExtra("Cvv").toString())

        mDialog.setTitle("Updating $CardNumber Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdatePayment.setOnClickListener {
            updatePayment(
                PaymentId,
                etCardNumber.text.toString(),
                etCardHolderName.text.toString(),
                etExpireMonth.text.toString(),
                etExpireYear.text.toString(),
                etCvv.text.toString()
            )

            Toast.makeText(applicationContext, "Payment Data Updated", Toast.LENGTH_LONG).show()

//we are setting updated data to our textviews
            tvCardNumber.text = etCardNumber.text.toString()
            tvCardHolderName.text = etCardHolderName.text.toString()
            tvExpireMonth.text = etExpireMonth.text.toString()
            tvExpireYear.text = etExpireYear.text.toString()
            tvCvv.text=etCvv.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updatePayment(
        id:String,
        cardnumber: String,
        cardholdername: String,
        expiremonth: String,
        expireyear: String,
        cvv: String,
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val PaymentsInfo = PaymentModel(id,cardnumber, cardholdername, expiremonth, expireyear,cvv)
        dbRef.setValue(PaymentsInfo)
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