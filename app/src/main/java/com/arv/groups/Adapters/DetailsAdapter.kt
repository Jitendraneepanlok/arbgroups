package com.arv.groups.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Activity.LoginActivity
import com.arv.groups.Activity.PaymentActivity
import com.arv.groups.Activity.ViewAllActivity
import com.arv.groups.FoxFun
import com.arv.groups.Model.DataModel
import com.arv.groups.R
import org.json.JSONArray
import org.json.JSONObject

class DetailsAdapter(var context: Context) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

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
        var tv_project_name_value: AppCompatTextView
        var tv_agent_number_value: AppCompatTextView
        var tv_agent_name_value: AppCompatTextView
        var tv_plot_number_value: AppCompatTextView
        var tv_plot_name_value: AppCompatTextView
        var txt_view: AppCompatTextView


        init {
            tv_project_name_value = itemView.findViewById(R.id.tv_project_name_value)
            tv_agent_number_value = itemView.findViewById(R.id.tv_agent_number_value)
            tv_agent_name_value = itemView.findViewById(R.id.tv_agent_name_value)
            tv_plot_number_value = itemView.findViewById(R.id.tv_plot_number_value)
            tv_plot_name_value = itemView.findViewById(R.id.tv_plot_name_value)
            txt_view = itemView.findViewById(R.id.txt_view)

            itemView.setOnClickListener {
                listner.onItemclicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.home_list, parent, false)
        return ViewHolder(view, mlistner)
    }

    override fun onBindViewHolder(holder: DetailsAdapter.ViewHolder, position: Int) {

        // Get the data based JsonObject on position
        var data: JSONObject = dataList[position] as JSONObject
        Log.e("ads", "" + data)
        var invoice_id: String = data.getString("InvoiceID")
        var projectName: String = data.getString("ProjectName")
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
        var agentcode :String = data.getString("agentcode")


        // Set item views based on your views and JsonObject
        holder.tv_project_name_value.text = projectName
        holder.tv_agent_number_value.text = agentNumber
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
        }
    }

    override fun getItemCount() = dataList.length()
}