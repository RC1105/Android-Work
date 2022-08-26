package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.launch
import java.util.*

class ResultActivity : AppCompatActivity() {
    private var username:String=""
    private var mRating:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        username= intent.getStringExtra(Constants.USER_NAME).toString()
        mRating=intent.getStringExtra(Constants.RATING).toString()
        val genre=intent.getStringExtra(Constants.GENRE)
        setName.text="Hey $username!"
        setGenre.text="In ${genre.toString()} Quiz"
        setRating.text="Rating: ${mRating.toString()}"
        val right=intent.getStringExtra(Constants.CORRECT_ANSWERS)
        val total=intent.getStringExtra(Constants.TOTAL_QUESTIONS)
        setResults.text="Your score is: ${right}/${total}"
        val dao = (application as EmployeeApp).db.employeeDao()
        insertRec(dao)

        iv_back.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        iv_leader.setOnClickListener {
            startActivity(Intent(this,LeaderBoard::class.java))
            //finish()
        }
        iv_erase.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("RESET LEADERBOARD")
            builder.setMessage("Are you sure you want to clear all entries of leaderboard?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes") { dialogInterface, which ->


                val dao = (application as EmployeeApp).db.employeeDao()
                reset(dao)
            }

            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()

        }
        iv_exit.setOnClickListener {
            onBackPressed()
        }
    }
    private fun insertRec(boardDao: EmployeeDao){
        if(mRating[0]!='-'){
            lifecycleScope.launch {
                boardDao.insert(EmployeeEntity(name=username, email = mRating))
            }
        }
        else{
            Toast.makeText(this,"Oops! Looks like you weren't prepared. No worries Leaderboard won't mention your score :)",Toast.LENGTH_LONG).show()
        }
    }
    private fun reset (employeeDao: EmployeeDao) {
        lifecycleScope.launch {
            employeeDao.delete()
        }
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