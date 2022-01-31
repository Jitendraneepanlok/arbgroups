package com.arv.groups.Activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.arv.groups.R
import com.arv.groups.prefrences.SessionManager
import com.google.android.material.textfield.TextInputEditText
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus

class PaymentOptionsActivity : AppCompatActivity(), PaymentStatusListener {
    private lateinit var easyUpiPayment: EasyUpiPayment
    private lateinit var field_vpa: TextInputEditText
    private lateinit var field_name: TextInputEditText
    private lateinit var field_payee_merchant_code: TextInputEditText
    private lateinit var field_transaction_id: TextInputEditText
    private lateinit var field_transaction_ref_id: TextInputEditText
    private lateinit var field_description: TextInputEditText
    private lateinit var field_amount: TextInputEditText
    private lateinit var radioAppChoice: RadioGroup
    private lateinit var img_back :AppCompatImageView
    private lateinit var PaidAmount : String
    private lateinit var button_pay : AppCompatButton
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_options)
        sessionManager = SessionManager(applicationContext)

        // get Data from Receiept adapter class
        if (intent.getStringExtra("InstallmentAmount")!=null) {
            PaidAmount = intent.getStringExtra("InstallmentAmount").toString()
        }

        initViews()
    }

    private fun initViews() {
        img_back = findViewById(R.id.img_back)
        img_back.setOnClickListener() {
            startActivity(Intent(this@PaymentOptionsActivity, ViewAllActivity::class.java))
            finish()
        }
        field_vpa = findViewById<TextInputEditText>(R.id.field_vpa)

        //here set user name from session Manager class into Edittext
        field_name = findViewById<TextInputEditText>(R.id.field_name)
        field_name.setText(sessionManager.getUserData(SessionManager.NAME).toString())

        field_payee_merchant_code = findViewById<TextInputEditText>(R.id.field_payee_merchant_code)
        field_transaction_id = findViewById<TextInputEditText>(R.id.field_transaction_id)
        field_transaction_ref_id = findViewById<TextInputEditText>(R.id.field_transaction_ref_id)
        field_description = findViewById<TextInputEditText>(R.id.field_description)

        // here set Installment amount from getting from ReceiptAdapter class
        field_amount = findViewById<TextInputEditText>(R.id.field_amount)
        field_amount.setText(PaidAmount)

        radioAppChoice = findViewById<RadioGroup>(R.id.radioAppChoice)


        val transactionId = "TID" + System.currentTimeMillis()
        field_transaction_id.setText(transactionId)
        field_transaction_ref_id.setText(transactionId)

        button_pay = findViewById<AppCompatButton>(R.id.button_pay)
        button_pay.setOnClickListener { pay() }
    }

    private fun pay() {


        val payeeVpa = field_vpa.text.toString()
        val payeeName = field_name.text.toString()
        val transactionId = field_transaction_id.text.toString()
        val transactionRefId = field_transaction_ref_id.text.toString()
        val payeeMerchantCode = field_payee_merchant_code.text.toString()
        val description = field_description.text.toString()
        val amount = field_amount.text.toString()
        val paymentAppChoice = radioAppChoice

        val paymentApp = when (paymentAppChoice.checkedRadioButtonId) {
            R.id.app_default -> PaymentApp.ALL
            R.id.app_amazonpay -> PaymentApp.AMAZON_PAY
            R.id.app_bhim_upi -> PaymentApp.BHIM_UPI
            R.id.app_google_pay -> PaymentApp.GOOGLE_PAY
            R.id.app_phonepe -> PaymentApp.PHONE_PE
            R.id.app_paytm -> PaymentApp.PAYTM
            else -> throw IllegalStateException("Unexpected value: " + paymentAppChoice.id)
        }

        try {
            // START PAYMENT INITIALIZATION
            easyUpiPayment = EasyUpiPayment(this) {
                this.paymentApp = paymentApp
                this.payeeVpa = payeeVpa
                this.payeeName = payeeName
                this.transactionId = transactionId
                this.transactionRefId = transactionRefId
                this.payeeMerchantCode = payeeMerchantCode
                this.description = description
                this.amount = amount
            }
            // END INITIALIZATION

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this)

            // Start payment / transaction
            easyUpiPayment.startPayment()
        } catch (e: Exception) {
            e.printStackTrace()
            toast("Error: ${e.message}")
        }
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        // Transaction Completed
        Log.e("TransactionId", transactionDetails.transactionId.toString())
        Log.e("TransactionResponseCode", transactionDetails.responseCode.toString())
        Log.e("TransactioRefnId", transactionDetails.transactionRefId.toString())

        transactionDetails.transactionId?.let { OpenSucessDialog(it) }

        //textView_status.text = transactionDetails.toString()

        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess()
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
    }

    private fun OpenSucessDialog(it: String) {
        val dialog = this?.let { Dialog(it, R.style.DialogTheme) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.payment_sucess_layout)
        dialog?.setCancelable(false)

        val txt_transaction_id :AppCompatTextView = dialog!!.findViewById(R.id.txt_transaction_id)
        txt_transaction_id.setText("Transaction Id : "+it)

        val txt_cancel: AppCompatTextView = dialog!!.findViewById(R.id.txt_cancel)
        txt_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog?.window!!.setGravity(Gravity.CENTER)
        dialog?.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }


    override fun onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user")
        //   imageView.setImageResource(R.drawable.ic_failed)
    }

    private fun onTransactionSuccess() {
        // Payment Success
        toast("Success")
        // imageView.setImageResource(R.drawable.ic_success)
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted")
        // imageView.setImageResource(R.drawable.ic_success)
    }

    private fun onTransactionFailed() {
        // Payment Failed
        toast("Failed")
        // imageView.setImageResource(R.drawable.ic_failed)
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}