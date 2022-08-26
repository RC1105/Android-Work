package com.example.rc11news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cat.*
import kotlinx.android.synthetic.main.activity_home.*

class CatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)
        iv_hm.setOnClickListener {
            val intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        iv_sch.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("genre","search")
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b")
            startActivity(intent)
            finish()

        }

        //Category
        business.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=business&language=en")
            intent.putExtra("genre","BUSINESS")
            startActivity(intent)
            finish()
        }
        entertainment.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=entertainment&language=en&country=in")
            intent.putExtra("genre","ENTERTAINMENT")
            startActivity(intent)
            finish()
        }
        evs.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=environment&language=en")
            intent.putExtra("genre","ENVIRONMENT")
            startActivity(intent)
            finish()
        }

        food.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=food&language=en")
            intent.putExtra("genre","FOOD")
            startActivity(intent)
            finish()
        }
        health.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=health&language=en")
            intent.putExtra("genre","HEALTH")
            startActivity(intent)
            finish()
        }
        politics.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=politics&language=en&country=in")
            intent.putExtra("genre","POLITICS")
            startActivity(intent)
            finish()
        }
        science.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=science&language=en")
            intent.putExtra("genre","SCIENCE")
            startActivity(intent)
            finish()
        }
        sports.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=sports&language=en&country=in")
            intent.putExtra("genre","SPORTS")
            startActivity(intent)
            finish()
        }
        tech.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=technology&language=en")
            intent.putExtra("genre","TECHNOLOGY")
            startActivity(intent)
            finish()
        }
        top.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=top&language=en")
            intent.putExtra("genre","TOP")
            startActivity(intent)
            finish()
        }
        world.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&category=world&language=en")
            intent.putExtra("genre","WORLD")
            startActivity(intent)
            finish()
        }



        //COUNTRY
        ind.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=in")
            intent.putExtra("genre","INDIA")
            startActivity(intent)
            finish()
        }
        us.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=us")
            intent.putExtra("genre","US")
            startActivity(intent)
            finish()
        }
        au.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=au")
            intent.putExtra("genre","AUSTRALIA")
            startActivity(intent)
            finish()
        }
        uk.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=gb")
            intent.putExtra("genre","UK")
            startActivity(intent)
            finish()
        }
        uae.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=ae")
            intent.putExtra("genre","UAE")
            startActivity(intent)
            finish()
        }
        ge.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=de")
            intent.putExtra("genre","GERMANY")
            startActivity(intent)
            finish()
        }







        //Language
        en.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=en")
            intent.putExtra("genre","ENGLISH")
            startActivity(intent)
            finish()
        }
        hi.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=hi")
            intent.putExtra("genre","HINDI")
            startActivity(intent)
            finish()
        }
        fr.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=fr")
            intent.putExtra("genre","FRENCH")
            startActivity(intent)
            finish()
        }


        de.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=de")
            intent.putExtra("genre","GERMAN")
            startActivity(intent)
            finish()
        }
        zh.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=zh")
            intent.putExtra("genre","CHINESE")
            startActivity(intent)
            finish()
        }
        jp.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=jp")
            intent.putExtra("genre","JAPANESE")
            startActivity(intent)
            finish()
        }


    }
}