package com.arv.groups.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import com.arv.groups.FoxFun
import com.arv.groups.R
import com.arv.groups.prefrences.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KParameter

class LoginActivity : AppCompatActivity() {

    private lateinit var textView2: AppCompatTextView
    private lateinit var etusername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etPasswordLayout: TextInputLayout
    private lateinit var cb_check: AppCompatCheckBox
    private lateinit var btn_send_otp: Button
    private lateinit var btn_login: Button

    lateinit var sessionManager: SessionManager
    var ischecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (checkForInternet(this)) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            var builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Please check your internet")

            builder.setIcon(R.drawable.ic_alert)
            builder.setPositiveButton("ok") { dialogInterface, which ->
                startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


        initView()
    }

    private fun initView() {
        cb_check = findViewById<AppCompatCheckBox>(R.id.cb_check)
        cb_check.setOnCheckedChangeListener { buttonView, isChecked ->
            ischecked = isChecked
        }


        textView2 = findViewById<AppCompatTextView>(R.id.textView2)
        textView2.setOnClickListener() {
            startActivity(Intent(this@LoginActivity, ForgotPassActivity::class.java))
            // close this activity
            finish()

        }

        etPasswordLayout = findViewById<TextInputLayout>(R.id.etPasswordLayout)
        btn_login = findViewById<Button>(R.id.btn_login)

        btn_send_otp = findViewById<Button>(R.id.btn_send_otp)
        btn_send_otp.setOnClickListener() {
            CheckValidation()
            // CallNewPage()
        }

        btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener() {
            CheckOtpValidation()
            // CallNewPage()
        }
    }

    private fun CheckOtpValidation() {
        etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val password: String = etPassword.text.toString()


        if (!password.equals("12345")) {
            etPassword.requestFocus()
            etPassword.setError("Field should not be Empty")
        } else {
            CallOtpVerifyApi()
        }
    }

    private fun CallOtpVerifyApi() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }

    private fun CheckValidation() {
        etusername = findViewById<TextInputEditText>(R.id.etusername)

        if (etusername.text.toString().isEmpty()) {
            etusername.requestFocus()
            etusername.setError("Field should not be Empty")
        } else {
            etPasswordLayout.visibility = View.VISIBLE
            btn_login.visibility = View.VISIBLE
            btn_send_otp.visibility = View.INVISIBLE
            cb_check.visibility = View.VISIBLE
            textView2.visibility = View.VISIBLE
            CallOtpApi()
        }
    }

    private fun CallOtpApi() {
        val pDialog = ProgressDialog(this@LoginActivity)
        pDialog.setMessage(application?.getString(R.string.dialog_msg));
        pDialog.setCancelable(false);
        pDialog.show();

        val username: String = "8447094063"
//        val password: String = "vbfvhjhjv"

        var mq: String =
            "SELECT TOP 1 Name,CareOfName,PhoneNumber FROM PropertyDetails WHERE LTRIM(RTRIM(PhoneNumber))" + "='" + username/*etusername.getText().toString()*/
                .toUpperCase() + "'"

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
                        for (i in 0 until Result.length()) {
                            val Name: String = FoxFun.getColVal(Result, i, "Name");
                            val CareofName: String = FoxFun.getColVal(Result, i, "CareOfName");
                            val PhoneNo: String = FoxFun.getColVal(Result, i, "PhoneNumber");


                            sessionManager = SessionManager(this@LoginActivity)
                            sessionManager.setValueBoolean(SessionManager.VALUE, ischecked)
                            sessionManager.setValue(SessionManager.NAME, Name)
                            sessionManager.setValue(SessionManager.CAREOFNAME, CareofName)
                            sessionManager.setValue(SessionManager.PHONENO, PhoneNo)

                            pDialog.dismiss()

                        }
                        /* try {
                             JSONArray Table2 = new JSONArray(Result1.getString("ANDROID_SETTINGS"));
                             ConstAll.compname = wservice_json.getColVal(Table2, 0, "COMPNAME");
                             ConstAll.TackInterval =
                                 wservice_json.getColVal(Table2, 0, "INTARWAL_TIME_SEC");
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }*/

                    } else {
                        pDialog.dismiss()
                        Toast.makeText(
                            this@LoginActivity,
                            "!Invalid Credientials.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                } else {
                    pDialog.dismiss()
                    Toast.makeText(this@LoginActivity, "!Invalid Credientials.", Toast.LENGTH_SHORT)
                        .show();
                }
            }

            override fun onError(Error: String?) {
                Log.e("ApiFail", "" + Error.toString())
                pDialog.dismiss()
            }
        })
    }

    private fun CallApi() {

        val pDialog = ProgressDialog(this@LoginActivity)
        pDialog.setMessage(application?.getString(R.string.dialog_msg));
        pDialog.setCancelable(false);
        pDialog.show();

        val username: String = "testing"
        val password: String = "vbfvhjhjv"

        var mq: String =
            "select * from users where username" + "='" + /*username*/etusername.getText()
                .toString()
                .toUpperCase() + "'and " + "password='" + /*password*/etPassword.getText()
                .toString() + "'"


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
                        for (i in 0 until Result.length()) {
                            val ID: String = FoxFun.getColVal(Result, i, "ID");
                            val USERNAME: String = FoxFun.getColVal(Result, i, "UserName");
                            val CREATEDAT: String = FoxFun.getColVal(Result, i, "CreatedDate");
                            val EXPIRE: String = FoxFun.getColVal(Result, i, "Expires");
                            val PASSWORD: String = FoxFun.getColVal(Result, i, "Password");
                            val status: String = FoxFun.getColVal(Result, i, "Status");
                            val Never_expire: String = FoxFun.getColVal(Result, i, "NeverExpire");

                            /*sessionManager = SessionManager(this@LoginActivity)
                            sessionManager.setValueBoolean(SessionManager.VALUE, ischecked)
                            sessionManager.setValue(SessionManager.USER_ID, ID)
                            sessionManager.setValue(SessionManager.USER_NAME, USERNAME)
                            sessionManager.setValue(SessionManager.PASSWORD, PASSWORD)
                            sessionManager.setValue(SessionManager.STATUS, status)
                            sessionManager.setValue(SessionManager.EXPIRES, EXPIRE)
                            sessionManager.setValue(SessionManager.CREATED_AT, CREATEDAT)
                            sessionManager.setValue(SessionManager.NEVER_EXPIRE, Never_expire)

                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))*/
                            pDialog.dismiss()

                        }
                        /* try {
                             JSONArray Table2 = new JSONArray(Result1.getString("ANDROID_SETTINGS"));
                             ConstAll.compname = wservice_json.getColVal(Table2, 0, "COMPNAME");
                             ConstAll.TackInterval =
                                 wservice_json.getColVal(Table2, 0, "INTARWAL_TIME_SEC");
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }*/

                    } else {
                        pDialog.dismiss()
                        Toast.makeText(
                            this@LoginActivity,
                            "!Invalid Credientials.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                } else {
                    pDialog.dismiss()
                    Toast.makeText(this@LoginActivity, "!Invalid Credientials.", Toast.LENGTH_SHORT)
                        .show();
                }
            }

            override fun onError(Error: String?) {
                Log.e("ApiFail", "" + Error.toString())
                pDialog.dismiss()

            }
        })

    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}