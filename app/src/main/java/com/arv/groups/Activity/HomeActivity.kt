package com.arv.groups.Activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Adapters.DetailsAdapter
import com.arv.groups.Model.DataModel
import com.arv.groups.R

class HomeActivity : AppCompatActivity() {

    private lateinit var recycler_details : RecyclerView
    private lateinit var adapter : DetailsAdapter
    private var dataList = mutableListOf<DataModel>()
    private lateinit var img_logout : AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
    }

    private fun initView() {
        img_logout = findViewById<AppCompatImageView>(R.id.img_logout)
        img_logout.setOnClickListener(){
            OpenLogoutDialog()
        }

        recycler_details = findViewById<RecyclerView>(R.id.recycler_details)
        recycler_details.layoutManager = LinearLayoutManager(applicationContext)
        adapter = applicationContext?.let { DetailsAdapter(it) }!!
        recycler_details.adapter = adapter
        Optionsdata()

        adapter.setOnItemClickListner(object : DetailsAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                Log.e("postion", "" + position)
                when (position) {
                    0 -> Log.e("postion 0", "")
                    1 -> Log.e("postion 1", "")
                    2 -> Log.e("postion 2", "")

                }
            }
        })
    }

    private fun OpenLogoutDialog() {
        val dialog = this?.let { Dialog(it, R.style.DialogTheme) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.custom_logout_layout)
        dialog?.setCancelable(true)

        val txt_cancel: AppCompatTextView = dialog!!.findViewById(R.id.txt_cancel)
        txt_cancel.setOnClickListener {
            dialog.dismiss()
        }

        val txt_yes: AppCompatTextView = dialog!!.findViewById(R.id.txt_yes)
        txt_yes.setOnClickListener {
            //callLogoutApi()
            callnewPage()
            dialog.dismiss()
        }

        dialog?.window!!.setGravity(Gravity.CENTER)
        dialog?.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun callnewPage() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))

    }

    private fun Optionsdata() {
        dataList.add(DataModel("101","Pay Now","1000.00","No",R.drawable.ic_print))
        dataList.add(DataModel("102","Pay Now","2000.00","Registered",R.drawable.ic_print))
        dataList.add(DataModel("103","Pay Now","4000.00","No",R.drawable.ic_print))
        dataList.add(DataModel("104","Pay Now","7000.00","No",R.drawable.ic_print))
        dataList.add(DataModel("105","Pay Now","5000.00","Registered",R.drawable.ic_print))
        dataList.add(DataModel("106","Pay Now","9000.00","No",R.drawable.ic_print))
        dataList.add(DataModel("107","Pay Now","10000.00","Registered",R.drawable.ic_print))
        dataList.add(DataModel("108","Pay Now","12000.00","No",R.drawable.ic_print))
        dataList.add(DataModel("109","Pay Now","3000.00","Registered",R.drawable.ic_print))
        dataList.add(DataModel("110","Pay Now","13000.00","No",R.drawable.ic_print))
        adapter.setDataList(dataList)
        adapter.notifyDataSetChanged()
    }
}