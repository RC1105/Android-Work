package com.example.cointoss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_flip_coin.*

class flipCoin : AppCompatActivity() {
    var player: String?=null
    var player2: String?=null
    var x:Int?=null
    var arrList= ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip_coin)

        var player:String=intent.getStringExtra("mp1").toString()
        arrList.add(player)
        var player2:String=intent.getStringExtra("mp2").toString()
        arrList.add(player2)
        val random:Int=(0..1).random()
        x=random
        greet.text="Hey ${arrList[x!!]}!"

    }

    fun heads(view: android.view.View) {
        val intent= Intent(this, toss::class.java)
        intent.putExtra("opt","Heads")
        intent.putExtra("mp1",arrList[0])
        intent.putExtra("mp2",arrList[1])
        intent.putExtra("called",arrList[x!!])
        startActivity(intent)
        finish()


    }
    fun tails(view: android.view.View) {
        val intent= Intent(this, toss::class.java)
        intent.putExtra("opt","Tails")

        intent.putExtra("mp1",arrList[0])
        intent.putExtra("mp2",arrList[1])
        intent.putExtra("called",arrList[x!!])
        startActivity(intent)
        finish()
    }
}