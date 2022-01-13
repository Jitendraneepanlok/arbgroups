package com.arv.groups.Activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Adapters.DetailsAdapter
import com.arv.groups.Adapters.PaymentAdapter
import com.arv.groups.Model.DataModel
import com.arv.groups.Model.paymentModel
import com.arv.groups.R
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat.startActivityForResult
/*import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails*/
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var recycler_payment: RecyclerView
    private lateinit var adapter: PaymentAdapter
    private var dataList = mutableListOf<paymentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        initView()

    }

    private fun initView() {
        recycler_payment = findViewById<RecyclerView>(R.id.recycler_payment)
        recycler_payment.layoutManager = LinearLayoutManager(applicationContext)
        adapter = applicationContext?.let { PaymentAdapter(it) }!!
        recycler_payment.adapter = adapter
        Optionsdata()

        adapter.setOnItemClickListner(object : PaymentAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                when (position) {
                    0 -> CallPhonePeUPI()
                }
            }
        })

        val tv_payment: AppCompatTextView = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.VISIBLE

        val img_back: AppCompatImageView = findViewById(R.id.img_back)
        img_back.setOnClickListener() {
            startActivity(Intent(this@PaymentActivity, ViewAllActivity::class.java))
        }
    }

    private fun CallPhonePeUPI() {
        
    }

    private fun Optionsdata() {
        dataList.add(paymentModel("Phone Pe", R.drawable.phonepe))
        dataList.add(paymentModel("Google Pe", R.drawable.googlepe))
        dataList.add(paymentModel("Paytm", R.drawable.paytm))
        dataList.add(paymentModel("Bhim", R.drawable.bhim))
        adapter.setDataList(dataList)
        adapter.notifyDataSetChanged()
    }
}