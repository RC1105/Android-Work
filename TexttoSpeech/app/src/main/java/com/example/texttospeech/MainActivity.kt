package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech?=null
    private var binding: ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        tts= TextToSpeech(this,this)
        btnSpeak.setOnClickListener{
            if(etEnteredText?.text!!.isEmpty()){
                Toast.makeText(this,"Please enter text to proceed",Toast.LENGTH_LONG).show()
            }
            else{
                speakOut(etEnteredText?.text.toString())
            }
        }
    }
    override fun onInit(status: Int){
        //super.onInit(pInt)
        if(status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("tts","lang not supported")
            }
        }

        else{
            Log.e("TTS","failed")
        }
    }
    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
    }
}