package com.arv.groups.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Activity.ViewAllActivity
import com.arv.groups.R
import org.json.JSONArray
import org.json.JSONObject

class ReceiptAdapter (var context: Context) : RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {

    var dataList = JSONArray()
    private lateinit var mlistner: onItemClickedListner

    internal fun setDataList(dataList: JSONArray) {
        this.dataList = dataList
    }

    interface onItemClickedListner {
        fun onItemclicked(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickedListner) {
        mlistner = listner
    }

    class ViewHolder(itemView: View, listner: onItemClickedListner) :
        RecyclerView.ViewHolder(itemView) {
        var tv_receipt_no: AppCompatTextView
       /* var tv_agent_number_value: AppCompatTextView
        var tv_agent_name_value: AppCompatTextView
        var tv_plot_number_value: AppCompatTextView
        var tv_plot_name_value: AppCompatTextView
        var txt_view: AppCompatTextView*/


        init {
            tv_receipt_no = itemView.findViewById(R.id.tv_receipt_no)
            /*tv_agent_number_value = itemView.findViewById(R.id.tv_agent_number_value)
            tv_agent_name_value = itemView.findViewById(R.id.tv_agent_name_value)
            tv_plot_number_value = itemView.findViewById(R.id.tv_plot_number_value)
            tv_plot_name_value = itemView.findViewById(R.id.tv_plot_name_value)
            txt_view = itemView.findViewById(R.id.txt_view)*/

            itemView.setOnClickListener {
                listner.onItemclicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.receipt_list, parent, false)
        return ViewHolder(view, mlistner)
    }

    override fun onBindViewHolder(holder: ReceiptAdapter.ViewHolder, position: Int) {

        // Get the data based JsonObject on position
        var data: JSONObject = dataList[position] as JSONObject
        Log.e("ads", "" + data)
        var invoice_id: String = data.getString("receiptnumber")
       /* var projectName: String = data.getString("ProjectName")
        var agentNumber: String = data.getString("agentNumber")
        var agentName: String = data.getString("AgentName")
        var plotNumber: String = data.getString("plotNumber")
        var planName: String = data.getString("PlanName")
        var installmentAmount: String = data.getString("InstallmentAmount")
        var propertyType: String = data.getString("PropertyType")
        var PerSquareCost: String = data.getString("PerSquareCost")
        var PropertySize: String = data.getString("PropertySize")
        var MeasurementType: String = data.getString("MeasurementType")
        var dimension: String = data.getString("dimension")
        var paidamount: String = data.getString("paidamount")
        var paidemi: String = data.getString("paidemi")
        var baseamount: String = data.getString("baseamount")
        var agentcode :String = data.getString("agentcode")*/


        // Set item views based on your views and JsonObject
        holder.tv_receipt_no.text = invoice_id
        /*holder.tv_agent_number_value.text = agentNumber
        holder.tv_agent_name_value.text = agentName
        holder.tv_plot_number_value.text = plotNumber
        holder.tv_plot_name_value.text = planName

        holder.txt_view.setOnClickListener() {
            val intent = Intent(context, ViewAllActivity::class.java)
            intent.putExtra("InvoiceID", invoice_id)
            intent.putExtra("ProjectName", projectName)
            intent.putExtra("agentNumber", agentNumber)
            intent.putExtra("AgentName", agentName)
            intent.putExtra("plotNumber", plotNumber)
            intent.putExtra("PlanName", planName)
            intent.putExtra("InstallmentAmount", installmentAmount)
            intent.putExtra("PropertyType", propertyType)
            intent.putExtra("PerSquareCost", PerSquareCost)
            intent.putExtra("PropertySize", PropertySize)
            intent.putExtra("MeasurementType", MeasurementType)
            intent.putExtra("dimension", dimension)
            intent.putExtra("paidamount", paidamount)
            intent.putExtra("paidemi", paidemi)
            intent.putExtra("baseamount", baseamount)
            intent.putExtra("agentcode",agentcode)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }*/
    }

    override fun getItemCount() = dataList.length()
}