package com.arv.groups.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Activity.ViewAllActivity
import com.arv.groups.R
import org.json.JSONArray
import org.json.JSONObject

class DetailsAdapter(var context: Context) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    var dataList = JSONArray()
    private lateinit var mlistner: onItemClickedListner
    private lateinit var invoice_id: String
    private lateinit var projectName: String
    private lateinit var agentNumber: String
    private lateinit var agentName: String
    private lateinit var plotNumber: String
    private lateinit var planName: String
    private lateinit var installmentAmount: String
    private lateinit var propertyType: String
    private lateinit var PerSquareCost: String
    private lateinit var PropertySize: String
    private lateinit var MeasurementType: String
    private lateinit var dimension: String
    private lateinit var paidamount: String
    private lateinit var paidemi: String
    private lateinit var baseamount: String
    private lateinit var agentcode: String

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

        if (data.getString("InvoiceID") != null) {
            invoice_id = data.getString("InvoiceID")
        }

        if (data.getString("ProjectName") != null) {
            projectName = data.getString("ProjectName")
        }

        if (data.getString("agentNumber") != null) {
            agentNumber = data.getString("agentNumber")

        }

        if (data.getString("AgentName") != null) {
            agentName = data.getString("AgentName")
        }

        if (data.getString("plotNumber") != null) {
            plotNumber = data.getString("plotNumber")
        }

        if (data.getString("PlanName") != null) {
            planName = data.getString("PlanName")
        }

        if (data.getString("InstallmentAmount") != null) {
            installmentAmount = data.getString("InstallmentAmount")

        }

        if (data.getString("PropertyType") != null) {
            propertyType = data.getString("PropertyType")

        }
        if (data.getString("PerSquareCost") != null) {
            PerSquareCost = data.getString("PerSquareCost")
        }

        if (data.getString("PropertySize") != null) {
            PropertySize = data.getString("PropertySize")

        }
        if (data.getString("MeasurementType") != null) {
            MeasurementType = data.getString("MeasurementType")

        }

        if (data.getString("dimension") != null) {
            dimension = data.getString("dimension")

        }


        if (data.getString("paidamount") != null) {
            paidamount = data.getString("paidamount")

        }


        if (data.getString("paidemi") != null) {
            paidemi = data.getString("paidemi")

        }

        if (data.getString("baseamount") != null) {
            baseamount = data.getString("baseamount")

        }

        if (data.getString("agentcode") != null) {
            agentcode = data.getString("agentcode")

        }


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
            intent.putExtra("agentcode", agentcode)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataList.length()
}