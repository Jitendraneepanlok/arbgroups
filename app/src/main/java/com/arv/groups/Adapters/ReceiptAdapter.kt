package com.arv.groups.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.R
import org.json.JSONArray
import org.json.JSONObject

class ReceiptAdapter(var context: Context) : RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {

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
        var receipt_date: AppCompatTextView
        var payent_mode: AppCompatTextView
        var payent_bank_name: AppCompatTextView
        var chequedate: AppCompatTextView
        var chequeno: AppCompatTextView
        var InstallmentAmount :AppCompatTextView
        var InstallmentDate :AppCompatTextView
        var paid_amount :AppCompatTextView
        var tv_notes :AppCompatTextView


        init {
            tv_receipt_no = itemView.findViewById(R.id.tv_receipt_no)
            receipt_date = itemView.findViewById(R.id.receipt_date)
            payent_mode = itemView.findViewById(R.id.payent_mode)
            payent_bank_name = itemView.findViewById(R.id.payent_bank_name)
            chequedate = itemView.findViewById(R.id.chequedate)
            chequeno = itemView.findViewById(R.id.chequeno)
            InstallmentAmount = itemView.findViewById(R.id.InstallmentAmount)
            InstallmentDate = itemView.findViewById(R.id.InstallmentDate)
            paid_amount = itemView.findViewById(R.id.paid_amount)
            tv_notes  =itemView.findViewById(R.id.tv_notes)


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
        var receiptnumber: String = data.getString("receiptnumber")
        var receiptdate: String = data.getString("receiptdate")
        var payentmode: String = data.getString("paymentmode")
        var paymentbankname: String = data.getString("paymentbankname")
        var chequedate: String = data.getString("chequedate")
        var chequeno: String = data.getString("chequeno")
        var InstallmentAmount :String = data.getString("InstallmentAmount")
        var InstallmentDate :String = data.getString("InstallmentDate")
        var paidamount :String = data.getString("paidamount")
        var notes :String = data.getString("notes")


        // Set item views based on your views and JsonObject
        holder.tv_receipt_no.text = receiptnumber
        holder.receipt_date.text = receiptdate
        holder.payent_mode.text = payentmode
        holder.payent_bank_name.text = paymentbankname
        holder.chequedate.text = chequedate
        holder.chequeno.text =chequeno
        holder.InstallmentAmount.text = InstallmentAmount
        holder.InstallmentDate.text = InstallmentDate
        holder.paid_amount.text = paidamount
        holder.tv_notes.text = notes

        /* holder.txt_view.setOnClickListener() {
             val intent = Intent(context, ViewAllActivity::class.java)

             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(intent)
         }*/
    }

    override fun getItemCount() = dataList.length()
}