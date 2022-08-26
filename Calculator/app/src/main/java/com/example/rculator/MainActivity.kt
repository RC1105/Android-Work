package com.example.rculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var lastDigit:Boolean=false
    var lastDeci:Boolean=false
    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastDigit=true
    }
    fun onClear(view: View){
        tvInput.text=""
        Toast.makeText(this,"Cleared!", Toast.LENGTH_LONG).show()
        lastDigit=false
        lastDeci=false
    }

    fun onDecimal(view: View){
        if (lastDigit && !(lastDeci)) {
            tvInput.append(".")
            lastDeci=true
            lastDigit=false
        }
        else if (!lastDeci){
            tvInput.append("0.")
            lastDeci=true
            lastDigit=false
        }
    }
    fun onOperator(view: View){
        if (lastDigit && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastDigit=false
            lastDeci=false
        }
    }
    fun onDel(view: View){
        val text = tvInput.text.toString()
        if (text.isNotEmpty()) {
            tvInput.text = text.subSequence(0,text.length-1)
        }
    }
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }
        else {
            value.contains("+")|| value.contains("-")||value.contains("/")|| value.contains("*")
        }
    }
    private fun removeDot(result:String):String{
        var value=result
        if (result.contains(".0")){
            value=result.substring(0,value.length-2)
        }
        return value
    }
    fun onEqual(view:View){
        if (lastDigit ){
            var tvValue=tvInput.text.toString()
            var prefix=""
            try{
                if (tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val ans=tvValue.split("-")
                    var op1=ans[0]
                    var op2=ans[1]

                    if (!prefix.isEmpty()){
                        op1=prefix+op1
                    }
                    tvInput.text=removeDot((op1.toDouble()-op2.toDouble()).toString())
                }
                else if (tvValue.contains("+")){
                    var ans=tvValue.split("+")
                    var op1=ans[0]
                    var op2=ans[1]
                    if (!prefix.isEmpty()){
                        op1=prefix+op1
                    }
                    tvInput.text=removeDot((op1.toDouble()+op2.toDouble()).toString())
                }

                else if (tvValue.contains("*")){
                    var ans=tvValue.split("*")
                    var op1=ans[0]
                    var op2=ans[1]
                    if (!prefix.isEmpty()){
                        op1=prefix+op1
                    }
                    tvInput.text=removeDot(((op1.toDouble())*(op2.toDouble())).toString())
                }
                else if (tvValue.contains("/")){
                    var ans=tvValue.split("/")
                    var op1=ans[0]
                    var op2=ans[1]
                    if (!prefix.isEmpty()){
                        op1=prefix+op1
                    }
                    tvInput.text=removeDot(( op1.toDouble() / op2.toDouble() ).toString())
                }


            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}