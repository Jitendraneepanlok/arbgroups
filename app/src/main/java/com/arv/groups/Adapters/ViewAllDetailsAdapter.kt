package com.arv.groups.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Activity.PaymentActivity
import com.arv.groups.Activity.ViewAllActivity
import com.arv.groups.Model.ViewAllDetailsDataModel
import com.arv.groups.R

class ViewAllDetailsAdapter (var context: Context) : RecyclerView.Adapter<ViewAllDetailsAdapter.ViewHolder>() {

    var dataList = emptyList<ViewAllDetailsDataModel>()
    private lateinit var mlistner: onItemClickedListner

    internal fun setDataList(dataList: List<ViewAllDetailsDataModel>) {
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
        var image: AppCompatImageView

        var tv_name_value : AppCompatTextView
        var tv_age_value : AppCompatTextView
        var plot_no_value : AppCompatTextView
        var tv_project_value : AppCompatTextView
        var tv_emi_amount: AppCompatTextView
        var tv_paid_amount: AppCompatTextView
        var tv_instalment_date: AppCompatTextView
        var tv_paid_date : AppCompatTextView
//        var tv_pay_status: AppCompatTextView

        init {
            image = itemView.findViewById(R.id.image)

            tv_name_value = itemView.findViewById(R.id.tv_name_value)
            tv_age_value = itemView.findViewById(R.id.tv_age_value)
            plot_no_value = itemView.findViewById(R.id.plot_no_value)
            tv_project_value = itemView.findViewById(R.id.tv_project_value)
            tv_emi_amount = itemView.findViewById(R.id.tv_emi_amount)
            tv_instalment_date = itemView.findViewById(R.id.tv_instalment_date)
            tv_paid_date = itemView.findViewById(R.id.tv_paid_date)
            tv_paid_amount = itemView.findViewById(R.id.tv_paid_amount)
          //  tv_pay_status = itemView.findViewById(R.id.tv_pay_status)

            itemView.setOnClickListener {
                listner.onItemclicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllDetailsAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, mlistner)
    }

    override fun onBindViewHolder(holder: ViewAllDetailsAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        var data = dataList[position]

        // Set item views based on your views and data model
        holder.plot_no_value.text = data.plot_no
        holder.tv_age_value.text = data.age
        holder.tv_project_value.text = data.project
        holder.tv_name_value.text  = data.name
        holder.tv_emi_amount.text = data.emi_amount
        holder.tv_paid_amount.text = data.paid_amount
        holder.tv_instalment_date.text = data.installment_date
        holder.tv_paid_date.text = data.paid_date
       /* holder.tv_pay_status.text = data.paid_status
        holder.tv_pay_status.setOnClickListener() {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }*/

        holder.image.setImageResource(data.image)

    }

    override fun getItemCount() = dataList.size
}