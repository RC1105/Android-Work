package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_leader_board.*
import kotlinx.android.synthetic.main.activity_leader_board.iv_erase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LeaderBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_leader_board)
        val dao = (application as EmployeeApp).db.employeeDao()
        setupListOfDataIntoRecyclerView(dao)
        iv_back.setOnClickListener {
            finish()
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

        iv_new.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("NEW GAME")
            builder.setMessage("Are you sure you want start a New Game?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()

        }
    }

    private fun setupListOfDataIntoRecyclerView(employeeDao: EmployeeDao) {

        lifecycleScope.launch {

            employeeDao.fetchAllEmployee().collect { emp ->

                if (emp.isNotEmpty()) {

                    // Set the LayoutManager that this RecyclerView will use.
                    rvItemsList.layoutManager = LinearLayoutManager(this@LeaderBoard)
                    // adapter instance is set to the recyclerview to inflate the items.
                    val dates = ArrayList<EmployeeEntity>()
                    for (date in emp) {

                        var be=EmployeeEntity(date.id,date.name,date.email)
                        dates.add(be)

                    }
                    val itemAdapter = ItemAdapter(ArrayList(dates))
                    congo.visibility=View.VISIBLE
                    rvItemsList.adapter = itemAdapter
                    rvItemsList.visibility = View.VISIBLE
                    header.visibility=View.VISIBLE
                    tvNoRecordsAvailable.visibility = View.GONE
                } else {
                    congo.visibility=View.INVISIBLE
                    rvItemsList.visibility = View.GONE
                    header.visibility=View.INVISIBLE
                    tvNoRecordsAvailable.visibility = View.VISIBLE
                }

            }


        }

    }
    private fun reset (employeeDao: EmployeeDao) {

        lifecycleScope.launch {
            employeeDao.delete()

        }
    }
}