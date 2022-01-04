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
import com.arv.groups.Model.paymentModel
import com.arv.groups.R

class PaymentAdapter (var context: Context) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    var dataList = emptyList<paymentModel>()
    private lateinit var mlistner: onItemClickedListner

    internal fun setDataList(dataList: List<paymentModel>) {
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
        var img_pay_icon: AppCompatImageView
        var tv_app_name: AppCompatTextView
        
        init {
            img_pay_icon = itemView.findViewById(R.id.img_pay_icon)
            tv_app_name = itemView.findViewById(R.id.tv_app_name)
            
            itemView.setOnClickListener {
                listner.onItemclicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.pay_list, parent, false)
        return ViewHolder(view, mlistner)
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        var data = dataList[position]
        // Set item views based on your views and data model
        holder.tv_app_name.text = data.name
        holder.img_pay_icon.setImageResource(data.image)


    }


    override fun getItemCount() = dataList.size
}