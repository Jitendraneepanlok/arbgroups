package com.arv.groups.Activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.arv.groups.R
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.arv.groups.Adapters.DetailsAdapter
import com.arv.groups.Adapters.ReceiptAdapter
import com.arv.groups.FoxFun
import com.arv.groups.prefrences.SessionManager
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ViewAllActivity : AppCompatActivity() {

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
    private lateinit var pdfdownload: AppCompatButton
    private lateinit var btn_pay: AppCompatButton
    private lateinit var tv_payment: AppCompatTextView
    private lateinit var img_back: AppCompatImageView

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

    private lateinit var reciept_recycler: RecyclerView
    private lateinit var adapter: ReceiptAdapter
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        sessionManager = SessionManager(applicationContext)
        initView()
        getRecieptData()
    }


    private fun initView() {
        if (intent.getStringExtra("InstallmentAmount") != null) {
            InstallmentAmount = intent.getStringExtra("InstallmentAmount").toString()
            tv_instalment_amount_value = findViewById<AppCompatTextView>(R.id.tv_instalment_amount_value)
            tv_instalment_amount_value.setText(InstallmentAmount)
        }

        if (intent.getStringExtra("plotNumber") != null) {
            plotNumber = intent.getStringExtra("plotNumber").toString()
            tv_plot_number_value = findViewById<AppCompatTextView>(R.id.tv_plot_number_value)
            tv_plot_number_value.setText(plotNumber)
        }


        if (intent.getStringExtra("ProjectName") != null) {
            ProjectName = intent.getStringExtra("ProjectName").toString()
            tv_project_name_value = findViewById<AppCompatTextView>(R.id.tv_project_name_value)
            tv_project_name_value.setText(ProjectName)
        }



        if (intent.getStringExtra("PropertyType") != null) {
            PropertyType = intent.getStringExtra("PropertyType").toString()
            tv_property_type_value = findViewById<AppCompatTextView>(R.id.tv_property_type_value)
            tv_property_type_value.setText(PropertyType)
        }

        if (intent.getStringExtra("agentcode") != null) {
            agentcode = intent.getStringExtra("agentcode").toString()
            tv_agent_code_value = findViewById<AppCompatTextView>(R.id.tv_agent_code_value)
            tv_agent_code_value.setText(agentcode)
        }


        if (intent.getStringExtra("agentNumber") != null) {
            agentNumber = intent.getStringExtra("agentNumber").toString()
            tv_agent_number_value = findViewById<AppCompatTextView>(R.id.tv_agent_number_value)
            tv_agent_number_value.setText(agentNumber)
        }

        if (intent.getStringExtra("PerSquareCost") != null) {
            PerSquareCost = intent.getStringExtra("PerSquareCost").toString()
            tv_pre_square_cost_value =
                findViewById<AppCompatTextView>(R.id.tv_pre_square_cost_value)
            tv_pre_square_cost_value.setText(PerSquareCost)
        }


        if (intent.getStringExtra("PropertySize") != null) {
            PropertySize = intent.getStringExtra("PropertySize").toString()
            tv_property_size_value = findViewById<AppCompatTextView>(R.id.tv_property_size_value)
            tv_property_size_value.setText(PropertySize)
        }


        if (intent.getStringExtra("PlanName") != null) {
            PlanName = intent.getStringExtra("PlanName").toString()
            tv_plan_name_value = findViewById<AppCompatTextView>(R.id.tv_plan_name_value)
            tv_plan_name_value.setText(PlanName)
        }


        if (intent.getStringExtra("MeasurementType") != null) {
            MeasurementType = intent.getStringExtra("MeasurementType").toString()
            tv_measurement_value = findViewById<AppCompatTextView>(R.id.tv_measurement_value)
            tv_measurement_value.setText(MeasurementType)
        }


        if (intent.getStringExtra("dimension") != null) {
            dimension = intent.getStringExtra("dimension").toString()
            tv_dimension_value = findViewById<AppCompatTextView>(R.id.tv_dimension_value)
            tv_dimension_value.setText(dimension)
        }


        if (intent.getStringExtra("paidamount") != null) {
            paidamount = intent.getStringExtra("paidamount").toString()
            tv_paid_amount_value = findViewById<AppCompatTextView>(R.id.tv_paid_amount_value)
            tv_paid_amount_value.setText(paidamount)
        }

        if (intent.getStringExtra("paidemi") != null) {
            paidemi = intent.getStringExtra("paidemi").toString()
            tv_paid_emi_value = findViewById<AppCompatTextView>(R.id.tv_paid_emi_value)
            tv_paid_emi_value.setText(paidemi)
        }
        if (intent.getStringExtra("baseamount") != null) {
            baseamount = intent.getStringExtra("baseamount").toString()
            tv_base_amount_value = findViewById<AppCompatTextView>(R.id.tv_base_amount_value)
            tv_base_amount_value.setText(baseamount)
        }


        if (intent.getStringExtra("AgentName") != null) {
            AgentName = intent.getStringExtra("AgentName").toString()
            tv_agent_name_value = findViewById<AppCompatTextView>(R.id.tv_agent_name_value)
            tv_agent_name_value.setText(AgentName)
        }

        if (intent.getStringExtra("InvoiceID") != null) {
            InvoiceID = intent.getStringExtra("InvoiceID").toString()
            tv_invoice_id_value = findViewById<AppCompatTextView>(R.id.tv_invoice_id_value)
            tv_invoice_id_value.setText(InvoiceID)
        }
        //toolbar showing textview

        tv_payment = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.VISIBLE
        tv_payment.setText("View All Details")
        img_back = findViewById(R.id.img_back)
        img_back.setOnClickListener() {
            startActivity(Intent(this@ViewAllActivity, HomeActivity::class.java))
        }

        /* btn_pay = findViewById<AppCompatButton>(R.id.btn_pay)
         btn_pay.setOnClickListener(){
             val intent :Intent = Intent(applicationContext,PaymentOptionsActivity::class.java)
             intent.putExtra("InstallmentAmount",InstallmentAmount)
            startActivity(intent)
         }*/

        pdfdownload = findViewById<AppCompatButton>(R.id.pdfdownload)
        pdfdownload.setOnClickListener()
        {
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

    private fun getRecieptData() {
        reciept_recycler = findViewById<RecyclerView>(R.id.reciept_recycler)
        reciept_recycler.layoutManager = LinearLayoutManager(applicationContext)
        adapter = applicationContext?.let { ReceiptAdapter(it) }!!
        reciept_recycler.adapter = adapter
        adapter.setOnItemClickListner(object : ReceiptAdapter.onItemClickedListner {
            override fun onItemclicked(position: Int) {
                when (position) {
                    0 -> Log.e("postion 0", "")
                    1 -> Log.e("postion 1", "")
                }
            }
        })

        val pDialog = ProgressDialog(this@ViewAllActivity)
        pDialog.setMessage(application?.getString(R.string.dialog_msg));
        pDialog.setCancelable(false);
        pDialog.show();

        /*val username: String = "8447094063"
        val plotnumber :String ="297"*/
        val userphone = sessionManager.getUserData(SessionManager.PHONENO).toString()

        var mq: String =
            "select receiptnumber,receiptdate,paymentmode,notes,paymentbankname,chequedate,chequeno , " +
                    "(select top 1 InstallmentAmount  from PropertyPlanDetail  pd where pd.InvoiceID=pl.InvoiceID and pd.PlanID=pl.PlanID)  InstallmentAmount " +
                    ",(select top 1 InstallmentDate  from PropertyPlanDetail  pd where pd.InvoiceID=pl.InvoiceID and pd.PlanID=pl.PlanID) " +
                    "InstallmentDate " + ",(paidamount) as paidamount " + "FROM PropertyDetails pl " +
                    " WHERE LTRIM(RTRIM(PhoneNumber))='" + userphone + "'   and PlotNumber='" + plotNumber + "' order by  InstallmentDate "

        FoxFun.getdatamod(this, mq, "", "", "", "", object : FoxFun.Callback {
            override fun onSuccess(Result1: JSONObject?) {
                if (Result1?.length()!! > 0) {
                    pDialog.dismiss()
                    var Result: JSONArray = JSONArray();
                    try {
                        Result = JSONArray(Result1.getString("Table1"));
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                    if (Result.length() > 0) {
                        pDialog.dismiss()

                        adapter.setDataList(Result)
                        adapter.notifyDataSetChanged()
                        pDialog.dismiss()
                        /* for (i in 0 until Result.length()) {
                             Log.e("dsa",""+ FoxFun.getColVal(Result, i, "receiptnumber"))

                             pDialog.dismiss()
                         }*/
                    } else {
                        pDialog.dismiss()
                        Toast.makeText(
                            this@ViewAllActivity,
                            "!Invalid Credientials.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    pDialog.dismiss()
                    Toast.makeText(
                        this@ViewAllActivity,
                        "!Invalid Credientials.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }

            override fun onError(Error: String?) {
                Log.e("ApiFail", "" + Error.toString())
                pDialog.dismiss()
            }
        })

    }

    private fun DownloadPdf() {

        PDFData =
            "Invoice ID : " + InvoiceID + "\n" + "Installment Amount : " + InstallmentAmount + "\n" + "Plot Number : " + plotNumber + "\n" + "Project Name : " + ProjectName + "\n" + "Property Type : " + PropertyType + "\n" + "Agent Code : " + agentcode + "\n" + "Agent Number : " + agentNumber + "\n" + "Agent Name : " + AgentName + "\n" + "Per Square Cost : " + PerSquareCost + "\n" + "Property Size : " + PropertySize + "\n" + "Plan Name : " + PlanName + "\n" + "Measurement Type : " + MeasurementType + "\n" + "Dimension : " + dimension + "\n" + "Paid Amount : " + paidamount + "\n" + "Paid Emi : " + paidemi + "\n" + "Base Amount : " + baseamount

        var document = PdfDocument()
        var pageInfo = PageInfo.Builder(300, 600, 1).create()
        var page = document.startPage(pageInfo)
        var canvas = page.canvas
        var paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(50f, 50f, 30f, paint)
        paint.color = Color.BLACK
        canvas.drawText(PDFData, 80f, 50f, paint)
        document.finishPage(page)
        pageInfo = PageInfo.Builder(300, 600, 2).create()
        page = document.startPage(pageInfo)
        canvas = page.canvas
        paint = Paint()
        paint.color = Color.BLUE
        canvas.drawCircle(100f, 100f, 100f, paint)
        document.finishPage(page)

        var directory_path = Environment.getExternalStorageDirectory().path + "/Arbgroup/"
        var file = File(directory_path)
        if (!file.exists()) {
            file.mkdirs()
        }
        var targetPdf = directory_path + ".pdf"
        var filePath = File(targetPdf)
        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this, "File Saved", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("main", "error $e")
            Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show()
        }
        // close the document
        document.close()
        /*val mDoc = Document()
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
        }*/
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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


