package com.example.appliancerepair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_mw.*
import kotlinx.android.synthetic.main.activity_rf.*
import java.util.*

class RF_Activity : AppCompatActivity() {
    private var othcheck: Boolean?=false
    private var compName: String?=""
    private var time:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rf)
        submitrf.setBackgroundResource(R.drawable.nope)
        rfdate_Picker.visibility= View.GONE
        et_rfdate.setOnClickListener{view->
            //clickDatePicker(view)
            et_rfdate.visibility= View.GONE
            rfdate_Picker.visibility= View.VISIBLE
            val datePicker = findViewById<DatePicker>(R.id.rfdate_Picker)
            val today = Calendar.getInstance()
            datePicker.minDate=System.currentTimeMillis()+(1000*24*60*60)
            datePicker.maxDate=System.currentTimeMillis()+ (1000*24*10*60*60)

            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) {
                    view, year, month, day ->
                val month = month + 1
                val msg = "$day/$month/$year"
                et_rfdate.setText(msg)
                rfdate_Picker.visibility= View.GONE
                et_rfdate.visibility= View.VISIBLE
                //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }

            //datePicker.setMinDate(System.currentTimeMillis());
        }
        rfcb.setOnClickListener {
            if(rfcb.isChecked){
                submitrf.setBackgroundResource(R.drawable.shape_button_rounded)
            }
            else{
                submitrf.setBackgroundResource(R.drawable.nope)
            }
        }
        submitrf.setOnClickListener {
            if(!rfcb.isChecked){
                Toast.makeText(this,"Please accept T&C", Toast.LENGTH_LONG).show()
            }
            else if(!rb_rffreezer.isChecked && !rb_rffridge.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields Type", Toast.LENGTH_LONG).show()
            }
            else if(!rb_rfage2.isChecked && !rb_rfage7.isChecked && !rb_rfage27.isChecked) {
                Toast.makeText(this, "Please fill all mandatory fields Age", Toast.LENGTH_LONG).show()
            }
            else if(!rb_rfsodi.isChecked && !rb_rfcool.isChecked && !rb_rfoth.isChecked && !rb_rfservice.isChecked && !rb_rftrip.isChecked){
                Toast.makeText(this,"Please fill all mandatory fields prob", Toast.LENGTH_LONG).show()
            }
            else if(othcheck==true && tv_rfoth.text.isNullOrEmpty()){
                Toast.makeText(this,"Please fill all mandatory fields Other", Toast.LENGTH_LONG).show()
            }
            else if(et_rfdate.text==" -- / -- / ----"){
                Toast.makeText(this,"Please fill all mandatory fields Date", Toast.LENGTH_LONG).show()
            }
            else{
                val intent=Intent(this,FinalActivity::class.java)
                intent.putExtra("appliance","Refrigerator")
                if(rb_rffridge.isChecked){
                    intent.putExtra("type","Fridge")
                }
                else{
                    intent.putExtra("type","Freezer")
                }
                if(othcheck==true){
                    intent.putExtra("company","${tv_rfoth.text.toString()}")
                }
                else{
                    intent.putExtra("company","${compName}")
                }
                intent.putExtra("date","${et_rfdate.text.toString()}")
                intent.putExtra("time","${time}")

                intent.putExtra("model","${rfmodel.text.toString()}")
                if(rb_rfage2.isChecked){
                    intent.putExtra("age","Less than 2")
                }
                else if(rb_rfage27.isChecked){
                    intent.putExtra("age","Between 2 to 7")
                }
                else{
                    intent.putExtra("age","More than 7")
                }

                if(rb_rfservice.isChecked){
                    intent.putExtra("problem","Servicing")
                }
                else if(rb_rfsodi.isChecked){
                    intent.putExtra("problem","Sensor or Drainage Issue")
                }
                else if(rb_rfcool.isChecked){
                    intent.putExtra("problem","Cooling issue")
                }
                else if(rb_rftrip.isChecked){
                    intent.putExtra("problem","Tripping issue")
                }
                else{
                    intent.putExtra("problem","Anonymous")
                }
                startActivity(intent)
            }
        }
        val languages = resources.getStringArray(R.array.company)
        val spinner = findViewById<Spinner>(R.id.rf_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    var fine=languages[position]

                    compName=fine
                    Toast.makeText(this@RF_Activity,
                        getString(R.string.selected_item) + " " +
                                "" + fine, Toast.LENGTH_SHORT).show()
                    if(languages[position]=="Other"){
                        othcheck=true

                        til_rfoth.visibility= View.VISIBLE
                        tv_rfoth.visibility= View.VISIBLE
                    }
                    else{
                        compName=fine
                        othcheck=false
                        til_rfoth.visibility= View.GONE
                        tv_rfoth.visibility= View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        val slott = resources.getStringArray(R.array.slots)
        val spinner2 = findViewById<Spinner>(R.id.rfspinner2)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slott)
            spinner2.adapter = adapter
            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    time=slott[position]
                    Toast.makeText(this@RF_Activity,
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

