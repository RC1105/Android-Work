package com.example.quiz

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            Quiz.visibility= View.VISIBLE
        },600)
        Handler().postDelayed({

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}