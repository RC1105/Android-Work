package com.example.appliancerepair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var runnable: Runnable? = null
        setContentView(R.layout.activity_main)
        var i=0
        var arr=ArrayList<Int>()
        arr.add(R.drawable.a)
        arr.add(R.drawable.b)
        arr.add(R.drawable.c)
        iv_gallery.setImageResource(arr[i])
        Handler().postDelayed(Runnable {
            Handler().postDelayed(runnable!!,3000)
            iv_gallery.setImageResource(arr[i])
            i++
            i=i%3
        }.also { runnable =it }, 20)
        login.setOnClickListener {
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
            //finish()
        }
        signup.setOnClickListener {
            val intent=Intent(this,Signup::class.java)
            startActivity(intent)
            //finish()
        }

        tv_viaPhone.setOnClickListener {
            val intent=Intent(this,otp::class.java)
            startActivity(intent)
            //finish()
        }
    }


}