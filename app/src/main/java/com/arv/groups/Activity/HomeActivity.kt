package com.arv.groups.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Adapters.DetailsAdapter
import com.arv.groups.FoxFun
import com.arv.groups.R
import com.arv.groups.prefrences.SessionManager
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    private lateinit var recycler_details: RecyclerView
    private lateinit var adapter: DetailsAdapter
    private lateinit var img_logout: AppCompatImageView
    private lateinit var img_profile: AppCompatImageView
    lateinit var sessionManager: SessionManager
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(applicationContext)
        initView()
        CallCardOneApi()
    }

    private fun initView() {
        img_logout = findViewById<AppCompatImageView>(R.id.img_logout)
        img_logout.setOnClickListener() {
            OpenLogoutDialog()
        }

        img_profile = findViewById<AppCompatImageView>(R.id.img_profile)
        img_profile.setOnClickListener() {
            OpenProfileDialog()
        }

        recycler_details = findViewById<RecyclerView>(R.id.recycler_details)
        recycler_details.layoutManager = LinearLayoutManager(applicationContext)
        adapter = applicationContext?.let { DetailsAdapter(it) }!!
        recycler_details.adapter = adapter
        adapter.setOnItemClickListner(object : DetailsAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                when (position) {
                    0 -> Log.e("postion 0", "")
                    1 -> Log.e("postion 1", "")
                }
            }
        })
    }

    private fun CallCardOneApi() {
        val pDialog = ProgressDialog(this@HomeActivity)
        pDialog.setMessage(application?.getString(R.string.dialog_msg));
        pDialog.setCancelable(false);
        pDialog.show();

        val username: String = "8447094063"
        var mq: String =
            "select InvoiceID, InstallmentAmount,plotNumber,ProjectName,PropertyType,AgentName,agentcode,agentNumber,\n" +
                    "PerSquareCost,PropertySize,PlanName,MeasurementType,dimension ,sum(paidamount) paidamount,count(paidamount)-1 as paidemi,max(baseamount) baseamount from (SELECT pl.InvoiceID,  (select top 1 InstallmentAmount  from PropertyPlanDetail  pd where pd.InvoiceID=pl.InvoiceID and pd.PlanID=pl.PlanID) InstallmentAmount,  plotNumber,ProjectName,PropertyType,AgentName,agentcode,agentNumber,PerSquareCost,PropertySize,PlanName,MeasurementType,dimension ,(pl.paidamount) as paidamount,baseamount \n" +
                    "FROM PropertyDetails pl WHERE LTRIM(RTRIM(PhoneNumber))='" + username + "') t group by InvoiceID, InstallmentAmount, plotNumber,ProjectName,PropertyType,AgentName,agentcode,agentNumber,PerSquareCost,PropertySize,PlanName,MeasurementType,dimension"

        FoxFun.getdatamod(this, mq, "", "", "", "", object : FoxFun.Callback {
            override fun onSuccess(Result1: JSONObject?) {
                if (Result1?.length()!! > 0) {
                    pDialog.dismiss()
                    var Result: JSONArray = JSONArray();
                    try {
                        Result = JSONArray(Result1.getString("Table1"));
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                    if (Result.length() > 0) {
                        pDialog.dismiss()

                        adapter.setDataList(Result)
                        adapter.notifyDataSetChanged()
                        pDialog.dismiss()

                        /*for (i in 0 until Result.length()) {
                            Log.e("dsa",""+FoxFun.getColVal(Result, i, "plotNumber"))

                            pDialog.dismiss()
                        }*/
                    } else {
                        pDialog.dismiss()
                        Toast.makeText(
                            this@HomeActivity,
                            "!Invalid Credientials.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    pDialog.dismiss()
                    Toast.makeText(this@HomeActivity, "!Invalid Credientials.", Toast.LENGTH_SHORT)
                        .show();
                }
            }

            override fun onError(Error: String?) {
                Log.e("ApiFail", "" + Error.toString())
                pDialog.dismiss()
            }
        })
    }


    private fun OpenProfileDialog() {
        val dialog = this?.let { Dialog(it, R.style.DialogTheme) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.profile_layout)
        dialog?.setCancelable(false)


        val tv_name: AppCompatTextView = dialog!!.findViewById(R.id.tv_name)
        tv_name.setText(sessionManager.getUserData(SessionManager.NAME).toString())

        val tv_care_of_name: AppCompatTextView = dialog!!.findViewById(R.id.tv_care_of_name)
        tv_care_of_name.setText(sessionManager.getUserData(SessionManager.CAREOFNAME).toString())

        val tv_phone_no: AppCompatTextView = dialog!!.findViewById(R.id.tv_phone_no)
        tv_phone_no.setText(sessionManager.getUserData(SessionManager.PHONENO).toString())

        val tv_close: AppCompatTextView = dialog!!.findViewById(R.id.tv_close)
        tv_close.setOnClickListener() {
            dialog.dismiss()
        }

        dialog?.window!!.setGravity(Gravity.TOP)
        dialog?.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    // when press logout icon
    private fun OpenLogoutDialog() {
        val dialog = this?.let { Dialog(it, R.style.DialogTheme) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.custom_logout_layout)
        dialog?.setCancelable(false)

        val txt_cancel: AppCompatTextView = dialog!!.findViewById(R.id.txt_cancel)
        txt_cancel.setOnClickListener {
            dialog.dismiss()
        }

        val txt_yes: AppCompatTextView = dialog!!.findViewById(R.id.txt_yes)
        txt_yes.setOnClickListener {
            //callLogoutApi()
            sessionManager.logout()
            callnewPage()
            dialog.dismiss()
        }

        dialog?.window!!.setGravity(Gravity.CENTER)
        dialog?.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    // go to login page when you logout
    private fun callnewPage() {
        finish()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }


    // back press from exit app again
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}