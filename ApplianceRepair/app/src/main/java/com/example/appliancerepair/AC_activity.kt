package com.example.appliancerepair

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isEmpty
import kotlinx.android.synthetic.main.activity_ac.*
import kotlinx.android.synthetic.main.activity_mw.*
import java.text.SimpleDateFormat
import java.util.*

class AC_activity : AppCompatActivity() {
    private var othcheck: Boolean?=false
    private var compName: String?=""
    private var time:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac)
        submitac.setBackgroundResource(R.drawable.nope)
        date_Picker.visibility=View.GONE
        et_date.setOnClickListener{view->
            //clickDatePicker(view)
            et_date.visibility=View.GONE
            date_Picker.visibility=View.VISIBLE
            val datePicker = findViewById<DatePicker>(R.id.date_Picker)
            val today = Calendar.getInstance()
            datePicker.minDate=System.currentTimeMillis()+(1000*24*60*60)
            datePicker.maxDate=System.currentTimeMillis()+ (1000*24*10*60*60)

            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) {
                    view, year, month, day ->
                val month = month + 1
                val msg = "$day/$month/$year"
                et_date.setText(msg)
                date_Picker.visibility=View.GONE
                et_date.visibility=View.VISIBLE
                //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }

            //datePicker.setMinDate(System.currentTimeMillis());
        }
        cb.setOnClickListener {
            if(cb.isChecked){
                submitac.setBackgroundResource(R.drawable.shape_button_rounded)
            }
            else{
                submitac.setBackgroundResource(R.drawable.nope)
            }
        }
        submitac.setOnClickListener {
            if(!cb.isChecked){
                Toast.makeText(this,"Please accept T&C",Toast.LENGTH_LONG).show()
            }
            else if(!rb_acsplit.isChecked && !rb_acwindow.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields Type",Toast.LENGTH_LONG).show()
            }
            else if(!rb_age2.isChecked && !rb_age7.isChecked && !rb_age27.isChecked) {
                Toast.makeText(this, "Please fill all mandatory fields Age", Toast.LENGTH_LONG).show()
            }
            else if(!rb_sodi.isChecked && !rb_cool.isChecked && !rb_oth.isChecked && !rb_service.isChecked && !rb_trip.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields prob",Toast.LENGTH_LONG).show()
            }
            else if(othcheck==true && tv_oth.text.isNullOrEmpty()){
                Toast.makeText(this,"Please fill all mandatory fields Other",Toast.LENGTH_LONG).show()
            }
            else if(et_date.text==" -- / -- / ----"){
                Toast.makeText(this,"Please fill all mandatory fields Date",Toast.LENGTH_LONG).show()
            }
            else{

                val intent= Intent(this,FinalActivity::class.java)
                intent.putExtra("appliance","Air Conditioner")
                if(rb_acwindow.isChecked){
                    intent.putExtra("type","Window AC")
                }
                else{
                    intent.putExtra("type","Split AC")
                }
                if(othcheck==true){
                    intent.putExtra("company","${tv_mwoth.text.toString()}")
                }
                else{
                    intent.putExtra("company","${compName}")
                }
                intent.putExtra("date","${et_date.text.toString()}")
                intent.putExtra("time","${time}")

                intent.putExtra("model","${model.text.toString()}")
                if(rb_age2.isChecked){
                    intent.putExtra("age","Less than 2")
                }
                else if(rb_age27.isChecked){
                    intent.putExtra("age","Between 2 to 7")
                }
                else{
                    intent.putExtra("age","More than 7")
                }

                if(rb_service.isChecked){
                    intent.putExtra("problem","Servicing")
                }
                else if(rb_sodi.isChecked){
                    intent.putExtra("problem","Sensor or Drainage Issue")
                }
                else if(rb_cool.isChecked){
                    intent.putExtra("problem","Cooling issue")
                }
                else if(rb_trip.isChecked){
                    intent.putExtra("problem","Tripping issue")
                }
                else{
                    intent.putExtra("problem","Anonymous")
                }
                startActivity(intent)

            }
        }
        val languages = resources.getStringArray(R.array.company)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@AC_activity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                    if(languages[position]=="Other"){
                        othcheck=true
                        til_oth.visibility=View.VISIBLE
                        tv_oth.visibility=View.VISIBLE
                    }
                    else{
                        othcheck=false
                        compName=languages[position]
                        til_oth.visibility=View.GONE
                        tv_oth.visibility=View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        val slott = resources.getStringArray(R.array.slots)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slott)
            spinner2.adapter = adapter
            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    time=slott[position]

                    Toast.makeText(this@AC_activity,
                          "Selected Slot: " +
                                "" + slott[position], Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        // SETUP DATE WALA FEATURE
        // replicate and make changes for other appliances too
    }
}
