package com.example.rc_ageapp

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import com.example.rc_ageapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val my = findViewById<Button>(R.id.btnDatePicker)
        my.setOnClickListener { view ->
            clickDatePicker(view)
            Toast.makeText(this, "Button works", Toast.LENGTH_LONG).show()
        }
    }
        fun clickDatePicker(view: View){
            val myCalendar= Calendar.getInstance()
            val day=myCalendar.get(Calendar.DAY_OF_MONTH)
            val month=myCalendar.get(Calendar.MONTH)
            val year=myCalendar.get(Calendar.YEAR)
            val dpd=DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{
                view,selectedYear,selectedMonth,selectedDayOfMonth->
                    Toast.makeText(this,"Date chosen: $selectedDayOfMonth / ${selectedMonth+1} / $selectedYear ",Toast.LENGTH_LONG).show()
                    var selectedDate="$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                    var db=findViewById<TextView>(R.id.dateBox)
                    db.setText(selectedDate)


                    var sdf=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    var t=sdf.parse(selectedDate)
                    val selectedDateInMinutes=t!!.time/60000

                    val currTime=sdf.parse(sdf.format(System.currentTimeMillis()))
                    val currTimeInMinutes=currTime!!.time/60000
                    val diff=currTimeInMinutes-selectedDateInMinutes
                    val ind=findViewById<TextView>(R.id.ageBox)
                    ind.setText(diff.toString()+" min")
                },year
                ,month
                ,day)
            dpd.datePicker.setMaxDate(Date().time-86400000)//yesterday => max option
            dpd.show()
            }
        }



