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
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails
import java.text.SimpleDateFormat
import java.util.*


class PaymentActivity : AppCompatActivity(), PaymentStatusListener {

    private  var REQ_UPIPAYMENT : Int = 0
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
                Log.e("postion", "" + position)
                when (position) {
                    0 -> CallPaymentUPI()
                    1 -> CallPaymentUPI()
                    2 -> CallPaymentUPI()
                    3 ->CallPaymentUPI()
                }
            }
        })

        val tv_payment: AppCompatTextView = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.VISIBLE

        val img_back: AppCompatImageView = findViewById(R.id.img_back)
        img_back.setOnClickListener() {
            startActivity(Intent(this@PaymentActivity, HomeActivity::class.java))
            // close this activity
            finish()
        }
    }

    private fun CallPaymentUPI() {
        val c: Date = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
        val transcId: String = df.format(c)

        val easyUpiPayment = EasyUpiPayment.Builder()
            .with(this)
            .setPayeeVpa("9837427670@ybl")
            .setPayeeName("Jitendra")
            .setTransactionId(transcId)
            .setTransactionRefId(transcId)
            .setDescription("payment processing")
            .setAmount("10.00")
            .build()
        easyUpiPayment.startPayment()
        easyUpiPayment.setPaymentStatusListener(this)
    }

    /* private fun CallPhonePe() {
         val uri = Uri.parse("upi://pay").buildUpon()
                 .appendQueryParameter("pa", "9837427670@ybl")
                 .appendQueryParameter("pn", "Jitendra")
                 .appendQueryParameter("tn", "Pay for in-app purchase")
                 .appendQueryParameter("am", "20")
                 .appendQueryParameter("cu", "INR")
                 .build()

         val upiPayIntent = Intent(Intent.ACTION_VIEW)
         upiPayIntent.data = uri

         val chooser = Intent.createChooser(upiPayIntent, "Pay with")

         if (null != chooser.resolveActivity(packageManager)) {
             Log.e( "tag","UPI Payment resolved to activity")
             startActivityForResult(chooser, REQ_UPIPAYMENT)
         } else {
             Log.e( "tag","No activity found to handle UPI Payment")
         }
     }*/

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQ_UPIPAYMENT === requestCode) {
            if (RESULT_OK == resultCode) {
                Log.d(TAG, "UPI Payment successfull")
            } else {
                Log.d(TAG, "UPI Payment failed")
            }
        }
    }
*/

    private fun Optionsdata() {
        dataList.add(paymentModel("Phone Pe", R.drawable.phonepe))
        dataList.add(paymentModel("Google Pe", R.drawable.googlepe))
        dataList.add(paymentModel("Paytm", R.drawable.paytm))
        dataList.add(paymentModel("Bhim", R.drawable.bhim))
        adapter.setDataList(dataList)
        adapter.notifyDataSetChanged()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails?) {
        Log.e("TAG", "TRANSACTION Complete");
    }

    override fun onTransactionSuccess() {
        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
    }

    override fun onTransactionSubmitted() {
        Log.e("TAG", "TRANSACTION SUBMIT");
    }

    override fun onTransactionFailed() {
        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
    }

    override fun onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }

    override fun onAppNotFound() {
        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
    }
}