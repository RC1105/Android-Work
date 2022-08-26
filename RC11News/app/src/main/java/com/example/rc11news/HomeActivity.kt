package com.example.rc11news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cat.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        tv_quick.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=en&country=in")
            intent.putExtra("genre","TOP")
            startActivity(intent)
        }
        iv_cat.setOnClickListener {
            val intent= Intent(this,CatActivity::class.java)
            startActivity(intent)
            finish()
        }
        iv_search.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("genre","search")
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b")
            startActivity(intent)
            finish()
        }

    }
}