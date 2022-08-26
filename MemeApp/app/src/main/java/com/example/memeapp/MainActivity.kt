package com.example.memeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var uri:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchMeme()
        iv_nexx.setOnClickListener {
            fetchMeme()
        }
        iv_share.setOnClickListener{
            shareMeme()
        }
    }
    private fun fetchMeme(){
        val queue=Volley.newRequestQueue(this)
        val url="https://meme-api.herokuapp.com/gimme"
        val jsonReq=JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                val url=response.getString("url")
                uri=url
                val auth= response.getString("author")
                val nfs= response.getString("nsfw")
                if(nfs=="true"){
                    fetchMeme()
                }
                else{
                    Glide.with(this).load(url).into(iv_magic)
                    tv_credits.text="Credits to the Author: \n"+auth.toString()

                }          },
            {
                Toast.makeText(this,"Something went Wrong",Toast.LENGTH_LONG).show()
            })
        queue.add(jsonReq)
    }
    private fun shareMeme(){
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Check this out what I found on Reddit ${uri}")
        val chooser=Intent.createChooser(intent,"Share this meme using")
        startActivity(chooser)
    }
}