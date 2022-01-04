package com.arv.groups.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.Activity.LoginActivity
import com.arv.groups.Activity.PaymentActivity
import com.arv.groups.Activity.ViewAllActivity
import com.arv.groups.Model.DataModel
import com.arv.groups.R

class DetailsAdapter(var context: Context) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    var dataList = emptyList<DataModel>()
    private lateinit var mlistner: onItemClickedListner

    internal fun setDataList(dataList: List<DataModel>) {
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
        var tv_plot_value: AppCompatTextView
        var tv_due_amount_value: AppCompatTextView
        var tv_full_final_value: AppCompatTextView
        var tv_pay_status: AppCompatTextView
        var txt_view :AppCompatTextView


        init {
            image = itemView.findViewById(R.id.image)
            tv_plot_value = itemView.findViewById(R.id.tv_plot_value)
            tv_due_amount_value = itemView.findViewById(R.id.tv_due_amount_value)
            tv_full_final_value = itemView.findViewById(R.id.tv_full_final_value)
            tv_pay_status = itemView.findViewById(R.id.tv_pay_status)
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

        // Get the data model based on position
        var data = dataList[position]

        // Set item views based on your views and data model
        holder.tv_plot_value.text = data.plot_number
        holder.tv_due_amount_value.text = data.due_amount
        holder.tv_full_final_value.text = data.full_final
        holder.tv_pay_status.text = data.paid_status
        holder.tv_pay_status.setOnClickListener() {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)        }

        holder.image.setImageResource(data.image)

        holder.txt_view.setOnClickListener() {
            val intent = Intent(context, ViewAllActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }

    }

    private fun OpenDialog() {
        val dialog = context?.let { Dialog(context, R.style.DialogTheme) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.payment_layout)
        dialog?.setCancelable(true)

        val img_close: AppCompatImageView = dialog!!.findViewById(R.id.img_close)
        img_close.setOnClickListener {
            dialog.dismiss()
        }

        dialog?.window!!.setGravity(Gravity.CENTER)
        dialog?.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    override fun getItemCount() = dataList.size
}