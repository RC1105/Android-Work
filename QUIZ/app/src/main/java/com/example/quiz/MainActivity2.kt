package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_start
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private var hhh:String?=null
    private var gen:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        hhh=intent.getStringExtra(Constants.USER_NAME)
        tv_naam.text="Hey $hhh!"
        btn_start1.setOnClickListener{
            gen="Car"
            var intent= Intent(this,QuizQuestionsActivity::class.java)
            intent.putExtra(Constants.USER_NAME,hhh.toString())
            intent.putExtra(Constants.GENRE,gen.toString())
            startActivity(intent)
            finish()
        }

        btn_start2.setOnClickListener {
            gen="GK"
            var intent=Intent(this,QuizQuestionsActivity2::class.java)
            intent.putExtra(Constants.USER_NAME,hhh.toString())
            intent.putExtra(Constants.GENRE,gen.toString())
            startActivity(intent)
            finish()
        }
    }

    fun back(view: View) {
        val intent= Intent(this,MainActivity::class.java)
        val mUserName=intent.getStringExtra(Constants.USER_NAME)
        intent.putExtra(Constants.USER_NAME,mUserName)
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("CLOSING APP")
        builder.setMessage("Are you sure you want to Close the app?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            super.onBackPressed()
        }

        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()

    }
}