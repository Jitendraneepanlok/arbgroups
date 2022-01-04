package com.arv.groups.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Adapters.DetailsAdapter
import com.arv.groups.Adapters.ViewAllDetailsAdapter
import com.arv.groups.Model.DataModel
import com.arv.groups.Model.ViewAllDetailsDataModel
import com.arv.groups.R

class ViewAllActivity : AppCompatActivity() {

    private lateinit var recycler_View_all_details : RecyclerView
    private lateinit var adapter : ViewAllDetailsAdapter
    private var dataList = mutableListOf<ViewAllDetailsDataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        initView()
    }

    private fun initView() {
        recycler_View_all_details = findViewById<RecyclerView>(R.id.recycler_View_all_details)
        recycler_View_all_details.layoutManager = LinearLayoutManager(applicationContext)
        adapter = applicationContext?.let { ViewAllDetailsAdapter(it) }!!
        recycler_View_all_details.adapter = adapter
        Optionsdata()

        adapter.setOnItemClickListner(object : ViewAllDetailsAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                Log.e("postion", "" + position)
                when (position) {
                    0 -> Log.e("postion 0", "")
                    1 -> Log.e("postion 1", "")
                    2 -> Log.e("postion 2", "")

                }
            }
        })
        val tv_payment : AppCompatTextView = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.INVISIBLE
        val img_back : AppCompatImageView = findViewById(R.id.img_back)
        img_back.setOnClickListener(){
            startActivity(Intent(this@ViewAllActivity, HomeActivity::class.java))
            // close this activity
            finish()
        }
    }

    private fun Optionsdata() {
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        dataList.add(ViewAllDetailsDataModel("Test","5 Years","A420","SRS Housing","12","10000.00","1-Jan-2021","29-Dec-2021","Paid",R.drawable.ic_print))
        adapter.setDataList(dataList)
        adapter.notifyDataSetChanged()
    }
}