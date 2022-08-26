package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN //full screen :)
        btn_start.setOnClickListener {
            if (et_name.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name to Proceed", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                startActivity(intent)
                finish()

            }

        }
        iv_leader.setOnClickListener {
            val intent = Intent(this, LeaderBoard::class.java)
            startActivity(intent)
        }
        iv_exit.setOnClickListener {
            onBackPressed()
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