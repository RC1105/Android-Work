package com.example.rc11news

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.android.synthetic.main.item_news.view.*

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsAdapter
    lateinit var  url:String //="https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=in&category=politics&language=en"
    lateinit var  gen:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customProgressDialog= Dialog(this)
        customProgressDialog.setContentView(R.layout.plz_act)
        rv.layoutManager = LinearLayoutManager(this)
        url= intent.getStringExtra("urrl").toString()
        gen= intent.getStringExtra("genre").toString()
        if(gen=="search"){
            action.visibility= View.GONE
            searchMe.visibility=View.VISIBLE
        }
        sv.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("genre","CUSTOM")
            intent.putExtra("urrl","https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&language=en&qInTitle=${et_cs.text.toString()}")
            et_cs.text.clear()
            startActivity(intent)
        }
        tv_title.text="RC XI:\t${gen.toString()} NEWS"
        if(gen!="search"){
            customProgressDialog.show()
            fetchData(url)
            Handler().postDelayed({
                customProgressDialog.cancel()
            },2000)
        }


        mAdapter = NewsAdapter(this)
        rv.adapter = mAdapter
        iniRefreshListener()
        iv_back.setOnClickListener {
            val intent=Intent(this,CatActivity::class.java)
            startActivity(intent)
            finish()
        }
        iv_cat2.setOnClickListener {
            val intent=Intent(this,CatActivity::class.java)
            startActivity(intent)
            finish()
        }
        iv_home2.setOnClickListener {
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchData(url: String) {
        val queue = Volley.newRequestQueue(this)

        //val url="https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=in&category=politics&language=en"
        //val url = "https://newsdata.io/api/1/news?apikey=pub_9890af73792b26ba394bbcccb825c712d54b&country=in&category=politics&language=en"
        //val url="https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        //val url="https://newsapi.org/v2/top-headlines?country=us&apiKey=a32ebacd78014d489d5c80b1a4c4e7ac"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //Toast.makeText(this,"Congo its working",Toast.LENGTH_LONG).show()
                val newsJsonArray = response.getJSONArray("results")
                val newsArr = ArrayList<News>()
                if(newsJsonArray.length()==0){
                    Toast.makeText(this,"Couldn't fetch any News",Toast.LENGTH_LONG).show()
                }
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("source_id"),
                        newsJsonObject.getString("pubDate"),
                        newsJsonObject.getString("image_url"),
                        newsJsonObject.getString("link"),
                    )
                    newsArr.add(news)
                }
                mAdapter.updateNews(newsArr)
            },
            {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonObjectRequest)
    }

    override fun onItemClick(item: News) {
        var customTabsIntent :CustomTabsIntent
        Toast.makeText(this, " Opening Article", Toast.LENGTH_LONG).show()
        val builder= CustomTabsIntent.Builder()
        customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.link))
    }

    fun iniRefreshListener() {
        swipe_layout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { // function is called when user pull for refresh,
            fetchData(url)
            Handler().postDelayed({
                if (swipe_layout.isRefreshing()) {
                    swipe_layout.setRefreshing(false)
                }
            }, 2000)
        })
    }

    //a32ebacd78014d489d5c80b1a4c4e7ac

}