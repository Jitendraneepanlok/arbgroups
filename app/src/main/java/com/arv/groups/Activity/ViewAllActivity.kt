package com.arv.groups.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory.COURIER
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfName
import com.itextpdf.text.pdf.PdfName.COURIER
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ViewAllActivity : AppCompatActivity() {

    private lateinit var recycler_View_all_details: RecyclerView
    private lateinit var adapter: ViewAllDetailsAdapter
    private var dataList = mutableListOf<ViewAllDetailsDataModel>()

    private lateinit var tv_instalment_amount_value: AppCompatTextView
    private lateinit var tv_plot_number_value: AppCompatTextView
    private lateinit var tv_project_name_value: AppCompatTextView
    private lateinit var tv_property_type_value: AppCompatTextView
    private lateinit var tv_agent_code_value: AppCompatTextView
    private lateinit var tv_agent_number_value: AppCompatTextView
    private lateinit var tv_pre_square_cost_value: AppCompatTextView
    private lateinit var tv_property_size_value: AppCompatTextView
    private lateinit var tv_plan_name_value: AppCompatTextView
    private lateinit var tv_measurement_value: AppCompatTextView
    private lateinit var tv_dimension_value: AppCompatTextView
    private lateinit var tv_paid_amount_value: AppCompatTextView
    private lateinit var tv_paid_emi_value: AppCompatTextView
    private lateinit var tv_base_amount_value: AppCompatTextView
    private lateinit var tv_agent_name_value: AppCompatTextView
    private lateinit var tv_invoice_id_value: AppCompatTextView
    private lateinit var pdfdownload: AppCompatImageView
    private lateinit var btn_pay : AppCompatTextView

    private val STORAGE_CODE: Int = 100;

    private lateinit var InstallmentAmount: String
    private lateinit var plotNumber: String
    private lateinit var ProjectName: String
    private lateinit var PropertyType: String
    private lateinit var agentcode: String
    private lateinit var agentNumber: String
    private lateinit var PerSquareCost: String
    private lateinit var PropertySize: String
    private lateinit var PlanName: String
    private lateinit var MeasurementType: String
    private lateinit var dimension: String
    private lateinit var paidamount: String
    private lateinit var paidemi: String
    private lateinit var baseamount: String
    private lateinit var AgentName: String
    private lateinit var InvoiceID: String
    private lateinit var PDFData: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        initView()
    }

    private fun initView() {
        tv_instalment_amount_value = findViewById<AppCompatTextView>(R.id.tv_instalment_amount_value)
        InstallmentAmount = intent.getStringExtra("InstallmentAmount").toString()
        tv_instalment_amount_value.setText(InstallmentAmount)

        tv_plot_number_value = findViewById<AppCompatTextView>(R.id.tv_plot_number_value)
        plotNumber = intent.getStringExtra("plotNumber").toString()
        tv_plot_number_value.setText(plotNumber)

        tv_project_name_value = findViewById<AppCompatTextView>(R.id.tv_project_name_value)
        ProjectName = intent.getStringExtra("ProjectName").toString()
        tv_project_name_value.setText(ProjectName)

        tv_property_type_value = findViewById<AppCompatTextView>(R.id.tv_property_type_value)
        PropertyType = intent.getStringExtra("PropertyType").toString()
        tv_property_type_value.setText(PropertyType)

        tv_agent_code_value = findViewById<AppCompatTextView>(R.id.tv_agent_code_value)
        agentcode = intent.getStringExtra("agentcode").toString()
        tv_agent_code_value.setText(agentcode)

        tv_agent_number_value = findViewById<AppCompatTextView>(R.id.tv_agent_number_value)
        agentNumber = intent.getStringExtra("agentNumber").toString()
        tv_agent_number_value.setText(agentNumber)

        tv_pre_square_cost_value = findViewById<AppCompatTextView>(R.id.tv_pre_square_cost_value)
        PerSquareCost = intent.getStringExtra("PerSquareCost").toString()
        tv_pre_square_cost_value.setText(PerSquareCost)

        tv_property_size_value = findViewById<AppCompatTextView>(R.id.tv_property_size_value)
        PropertySize = intent.getStringExtra("PropertySize").toString()
        tv_property_size_value.setText(PropertySize)

        tv_plan_name_value = findViewById<AppCompatTextView>(R.id.tv_plan_name_value)
        PlanName = intent.getStringExtra("PlanName").toString()
        tv_plan_name_value.setText(PlanName)

        tv_measurement_value = findViewById<AppCompatTextView>(R.id.tv_measurement_value)
        MeasurementType = intent.getStringExtra("MeasurementType").toString()
        tv_measurement_value.setText(MeasurementType)

        tv_dimension_value = findViewById<AppCompatTextView>(R.id.tv_dimension_value)
        dimension = intent.getStringExtra("dimension").toString()
        tv_dimension_value.setText(dimension)

        tv_paid_amount_value = findViewById<AppCompatTextView>(R.id.tv_paid_amount_value)
        paidamount = intent.getStringExtra("paidamount").toString()
        tv_paid_amount_value.setText(paidamount)

        tv_paid_emi_value = findViewById<AppCompatTextView>(R.id.tv_paid_emi_value)
        paidemi = intent.getStringExtra("paidemi").toString()
        tv_paid_emi_value.setText(paidemi)

        tv_base_amount_value = findViewById<AppCompatTextView>(R.id.tv_base_amount_value)
        baseamount = intent.getStringExtra("baseamount").toString()
        tv_base_amount_value.setText(baseamount)

        tv_agent_name_value = findViewById<AppCompatTextView>(R.id.tv_agent_name_value)
        AgentName = intent.getStringExtra("AgentName").toString()
        tv_agent_name_value.setText(AgentName)

        tv_invoice_id_value = findViewById<AppCompatTextView>(R.id.tv_invoice_id_value)
        InvoiceID = intent.getStringExtra("InvoiceID").toString()
        tv_invoice_id_value.setText(InvoiceID)
       /*
        adapter.setOnItemClickListner(object : ViewAllDetailsAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                Log.e("postion", "" + position)
                when (position) {
                    0 -> Log.e("postion 0", "")
                    1 -> Log.e("postion 1", "")
                    2 -> Log.e("postion 2", "")

                }
            }
        })*/
        val tv_payment: AppCompatTextView = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.INVISIBLE
        val img_back: AppCompatImageView = findViewById(R.id.img_back)
        img_back.setOnClickListener() {
            startActivity(Intent(this@ViewAllActivity, HomeActivity::class.java))
        }

        btn_pay = findViewById<AppCompatTextView>(R.id.btn_pay)
        btn_pay.setOnClickListener(){
            startActivity(Intent(applicationContext,PaymentActivity::class.java))
        }

        pdfdownload = findViewById<AppCompatImageView>(R.id.pdfdownload)
        pdfdownload.setOnClickListener() {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                } else {
                    DownloadPdf()
                }
            } else {
                DownloadPdf()
            }
        }
    }

    private fun DownloadPdf() {

        PDFData = "Invoice ID : " + InvoiceID + "\n" + "Installment Amount : " + InstallmentAmount + "\n" + "Plot Number : " + plotNumber + "\n" + "Project Name : " + ProjectName + "\n" + "Property Type : " + PropertyType + "\n" + "Agent Code : " + agentcode + "\n" + "Agent Number : " + agentNumber + "\n" + "Agent Name : " + AgentName + "\n"+"Per Square Cost : " + PerSquareCost + "\n" + "Property Size : " + PropertySize + "\n" + "Plan Name : " + PlanName + "\n" + "Measurement Type : " + MeasurementType + "\n" + "Dimension : " + dimension + "\n" + "Paid Amount : " + paidamount + "\n" + "Paid Emi : " + paidemi + "\n" + "Base Amount : " + baseamount

        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFilePath = Environment.getExternalStorageDirectory()
            .toString() + "/" + "ARBGROUP " + mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()
            mDoc.addAuthor(application.getString(R.string.app_name))
            mDoc.add(Paragraph(PDFData))
            mDoc.close()
            Toast.makeText(applicationContext, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadPdf()
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


