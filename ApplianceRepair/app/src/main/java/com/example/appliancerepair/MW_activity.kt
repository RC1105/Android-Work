package com.example.appliancerepair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_ac.*
import kotlinx.android.synthetic.main.activity_mw.*
import kotlinx.android.synthetic.main.activity_rf.*
import java.util.*

class MW_activity : AppCompatActivity() {
    private var othcheck: Boolean?=false
    private var compName: String?=""
    private var time:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mw)
        submitmw.setBackgroundResource(R.drawable.nope)
        mwdate_Picker.visibility= View.GONE
        et_mwdate.setOnClickListener{view->
            //clickDatePicker(view)
            et_mwdate.visibility= View.GONE
            mwdate_Picker.visibility= View.VISIBLE
            val datePicker = findViewById<DatePicker>(R.id.mwdate_Picker)
            val today = Calendar.getInstance()
            datePicker.minDate=System.currentTimeMillis()+(1000*24*60*60)
            datePicker.maxDate=System.currentTimeMillis()+ (1000*24*10*60*60)

            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) {
                    view, year, month, day ->
                val month = month + 1
                val msg = "$day/$month/$year"
                et_mwdate.setText(msg)
                mwdate_Picker.visibility= View.GONE
                et_mwdate.visibility= View.VISIBLE
                //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }

            //datePicker.setMinDate(System.currentTimeMillis());
        }
        mwcb.setOnClickListener {
            if(mwcb.isChecked){
                submitmw.setBackgroundResource(R.drawable.shape_button_rounded)
            }
            else{
                submitmw.setBackgroundResource(R.drawable.nope)
            }
        }
        submitmw.setOnClickListener {
            if(!mwcb.isChecked){
                Toast.makeText(this,"Please accept T&C", Toast.LENGTH_LONG).show()
            }
            else if(!rb_mwotg.isChecked && !rb_mwmicrowave.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields Type", Toast.LENGTH_LONG).show()
            }
            else if(!rb_mwage2.isChecked && !rb_mwage7.isChecked && !rb_mwage27.isChecked) {
                Toast.makeText(this, "Please fill all mandatory fields Age", Toast.LENGTH_LONG).show()
            }
            else if(!rb_mwsodi.isChecked && !rb_mwcool.isChecked && !rb_mwoth.isChecked && !rb_mwservice.isChecked && !rb_mwtrip.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields prob", Toast.LENGTH_LONG).show()
            }
            else if(othcheck==true && tv_mwoth.text.isNullOrEmpty()){
                Toast.makeText(this,"Please fill all mandatory fields Other", Toast.LENGTH_LONG).show()
            }
            else if(et_mwdate.text==" -- / -- / ----"){
                Toast.makeText(this,"Please fill all mandatory fields Date", Toast.LENGTH_LONG).show()
            }
            else{
                val intent= Intent(this,FinalActivity::class.java)
                intent.putExtra("appliance","Microwave")
                if(rb_mwotg.isChecked){
                    intent.putExtra("type","OTG")
                }
                else{
                    intent.putExtra("type","Microwave")
                }
                if(othcheck==true){
                    intent.putExtra("company","${tv_mwoth.text.toString()}")
                }
                else{
                    intent.putExtra("company","${compName}")
                }
                intent.putExtra("date","${et_mwdate.text.toString()}")
                intent.putExtra("time","${time}")

                intent.putExtra("model","${mwmodel.text.toString()}")
                if(rb_mwage2.isChecked){
                    intent.putExtra("age","Less than 2")
                }
                else if(rb_mwage27.isChecked){
                    intent.putExtra("age","Between 2 to 7")
                }
                else{
                    intent.putExtra("age","More than 7")
                }

                if(rb_mwservice.isChecked){
                    intent.putExtra("problem","Servicing")
                }
                else if(rb_mwsodi.isChecked){
                    intent.putExtra("problem","Sensor or Drainage Issue")
                }
                else if(rb_mwcool.isChecked){
                    intent.putExtra("problem","Heating issue")
                }
                else if(rb_mwtrip.isChecked){
                    intent.putExtra("problem","Tripping issue")
                }
                else{
                    intent.putExtra("problem","Anonymous")
                }
                startActivity(intent)
            }
        }
        val languages = resources.getStringArray(R.array.company)
        val spinner = findViewById<Spinner>(R.id.mw_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    Toast.makeText(this@MW_activity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                    if(languages[position]=="Other"){
                        othcheck=true
                        til_mwoth.visibility= View.VISIBLE
                        tv_mwoth.visibility= View.VISIBLE
                    }
                    else{
                        othcheck=false
                        compName=languages[position]
                        til_mwoth.visibility= View.GONE
                        tv_mwoth.visibility= View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        val slott = resources.getStringArray(R.array.slots)
        val spinner2 = findViewById<Spinner>(R.id.mwspinner2)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slott)
            spinner2.adapter = adapter
            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    time=slott[position]
                    Toast.makeText(this@MW_activity,
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

