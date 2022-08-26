package com.example.cointoss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_player_select.*

class playerSelect : AppCompatActivity() {
    var mPlayer1: String?=null
    var mPlayer2: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_select)
        mPlayer1=player1.text.toString()
        mPlayer2=player2.text.toString()

        next.setOnClickListener{
            if((player1.text.toString()).isNullOrBlank() || (player2.text.toString()).isNullOrBlank()){
                Toast.makeText(this,"Name can't be empty :(",Toast.LENGTH_LONG).show()
            }
            else{
                val intent= Intent(this,flipCoin::class.java)
                intent.putExtra("mp1",player1.text.toString())
                intent.putExtra("mp2",player2.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}