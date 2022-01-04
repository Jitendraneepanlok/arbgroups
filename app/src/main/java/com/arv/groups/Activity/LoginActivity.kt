package com.arv.groups.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.arv.groups.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity(){

    private lateinit var textView2 : AppCompatTextView
    private lateinit var etusername : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var button : AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        textView2 = findViewById<AppCompatTextView>(R.id.textView2)
        textView2.setOnClickListener(){
            startActivity(Intent(this@LoginActivity, ForgotPassActivity::class.java))
            // close this activity
            finish()

        }

        button = findViewById<AppCompatButton>(R.id.button)
        button.setOnClickListener(){
           // CheckValidation()
            CallNewPage()
        }
    }

    private fun CheckValidation() {
        etusername = findViewById<TextInputEditText>(R.id.etusername)
        etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        if (etusername.text.toString().equals("admin")){
            etusername.requestFocus()
            etusername.setError("UserName should not be Empty")
        }else if (etPassword.text.toString().equals("12345")){
            etPassword.requestFocus()
            etPassword.setError("Password should not be Empty")
        }else{
            CallNewPage()
        }
    }
    private fun CallNewPage() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }


}