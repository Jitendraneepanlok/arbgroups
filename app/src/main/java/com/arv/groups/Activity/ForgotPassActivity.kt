package com.arv.groups.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.arv.groups.R

class ForgotPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        initView()
    }

    private fun initView() {
        val tv_payment : AppCompatTextView = findViewById(R.id.tv_payment)
        tv_payment.visibility = View.INVISIBLE

        val img_back : AppCompatImageView = findViewById(R.id.img_back)
        img_back.setOnClickListener(){
            startActivity(Intent(this@ForgotPassActivity, LoginActivity::class.java))
            // close this activity
            finish()
        }
    }
}