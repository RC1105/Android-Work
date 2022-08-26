package com.example.cointoss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_toss.*
import java.util.*
import kotlin.collections.ArrayList

class toss : AppCompatActivity(), TextToSpeech.OnInitListener {
    var wants:String?=null
    var who:String?=null
    var firstPlayer:String?=null
    var secondPlayer:String?=null
    var x:Int?=null
    var tts: TextToSpeech?=null
    var arr= ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toss)
        btnAgain.visibility=View.INVISIBLE
        wants=intent.getStringExtra("opt")
        who=intent.getStringExtra("called")
        tts=TextToSpeech(this,this)

        arr.add("Heads")
        arr.add("Tails")
        firstPlayer=intent.getStringExtra("mp1")
        secondPlayer=intent.getStringExtra("mp2")
        var random:Int=(0..1).random()
        x=random
        btnAgain.setOnClickListener{
            val intent=Intent(this,playerSelect::class.java)
            startActivity(intent)
            finish()
        }
    }



    fun magic(view: android.view.View) {
        //      tossMe.visibility = View.GONE

        for (i in 0..10000){
            if(i%2==0){
                tossMe.setImageResource(R.drawable.img)
            }
            if(i%2==1){
                tossMe.setImageResource(R.drawable.img_1)
            }
        }
        if(arr[x!!]=="Heads"){
            tossMe.setImageResource(R.drawable.img_1)
        }
        else{

                tossMe.setImageResource(R.drawable.img)

        }
        press.text="Congratulations!"
        outcome.text="It's "+arr[x!!].toString()
        if(arr[x!!]==wants.toString()){
            winner.text=who +" has won the toss"
        }
        else{
            if(who.toString()!=firstPlayer.toString()){
                winner.text=firstPlayer.toString() +" has won the toss"
            }

            if(who.toString()!=secondPlayer.toString()){
                winner.text=secondPlayer.toString() +" has won the toss"
            }

        }
        speakOut2()
        btnAgain.visibility=View.VISIBLE
    }
    private fun speakOut(){
        val text="${wants} is the call from ${who}"
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }
    private fun speakOut2(){
        val text="and ${arr[x!!]} it is!... So... ${winner.text.toString()}"
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
            else{
                speakOut()

            }/*
            else {
                buttonSpeak!!.isEnabled = true
            }*/

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}