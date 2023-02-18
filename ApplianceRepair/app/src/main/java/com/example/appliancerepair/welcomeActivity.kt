package com.example.appliancerepair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*

class welcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        RF.setOnClickListener {
            val intent= Intent(this, RF_Activity::class.java)
            startActivity(intent)
        }
        MW.setOnClickListener {
            val intent= Intent(this, MW_activity::class.java)
            startActivity(intent)
        }
        AC.setOnClickListener {
            val intent= Intent(this, AC_activity::class.java)
            startActivity(intent)
        }
    }
}