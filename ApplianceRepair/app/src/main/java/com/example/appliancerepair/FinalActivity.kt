package com.example.appliancerepair

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_final.*
import kotlinx.android.synthetic.main.activity_verify_otp.*

class FinalActivity : AppCompatActivity() {
    private var mType: String?=""
    private var mProblem: String?=""
    private var mDate: String?=""
    private var mTime: String?=""
    private var mCompany: String?=""
    private var mModel: String?=""
    private var mAge: String?=""
    private var mApplicance: String?=""



    override fun onCreate(savedInstanceState: Bundle?) {
        var vis: Boolean?= false
        var vis2: Boolean?= false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        mApplicance=intent.getStringExtra("appliance")
        mType=intent.getStringExtra("type")
        mProblem=intent.getStringExtra("problem")
        mDate=intent.getStringExtra("date")
        mTime=intent.getStringExtra("time")
        mCompany=intent.getStringExtra("company")
        mModel=intent.getStringExtra("model")
        mAge=intent.getStringExtra("age")

        tv_apprep.text=" APPLIANCE: ${mApplicance} \n COMPANY: ${mCompany} - ${mModel} \n AGE: ${mAge} \n TYPE: ${mType} \n PROBLEM: ${mProblem} "
        tv_dnt.text=" DATE: ${mDate} \n TIME SLOT: ${mTime} IST "
        ll_summary.setOnClickListener {
            if(vis==false){
                tv_summary.visibility= View.VISIBLE
                iv_down.setImageResource(R.drawable.ic_drop_up)
                vis=true
            }
            else{
                tv_summary.visibility=View.GONE
                iv_down.setImageResource(R.drawable.ic_drop_down)
                vis=false
            }
        }
        ll_personal.setOnClickListener {
            if(vis2==false){
                ll_personalInfo.visibility= View.VISIBLE
                iv_down2.setImageResource(R.drawable.ic_drop_up)
                vis2=true
            }
            else{
                ll_personalInfo.visibility=View.GONE
                iv_down2.setImageResource(R.drawable.ic_drop_down)
                vis2=false
            }
        }
        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Issues")

        tv_finalSubmit.setOnClickListener {
            if(!tv_final_mob.text.isNullOrEmpty() && !tv_final_name.text.isNullOrEmpty() && !tv_addr.text.isNullOrEmpty() && !tv_final_city.text.isNullOrEmpty() && !tv_final_state.text.isNullOrEmpty() && !tv_final_pincode.text.isNullOrEmpty()){
                Toast.makeText(this, "GREETINGS FROM RC11", Toast.LENGTH_LONG).show()
                var name=tv_final_name.text.toString()
                var mob=tv_final_mob.text.toString()
                var addr=tv_addr.text.toString()
                var city=tv_final_city.text.toString()
                var state=tv_final_state.text.toString()
                var pin=tv_final_pincode.text.toString()
                var dess=(tv_apprep.text.toString() + tv_dnt.text.toString())
                var appliance=mApplicance
                var company=mCompany
                var age=mAge
                var pro=mProblem
                var slot=mDate+" @"+mTime
                val problem= appli(name,mob,addr,city,state,pin,appliance,company,age,pro,slot)
                var emId=firebase.push().key!!
                firebase.child(emId).setValue(problem).addOnCompleteListener {
                    Toast.makeText(this,"Updation machha",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{it->
                    Toast.makeText(this,"Ahaaa ${it.message}",Toast.LENGTH_LONG).show()
                }

                /*
                mType: String?=""
                private var mProblem: String?=""
                private var mDate: String?=""
                private var mTime: String?=""
                private var mCompany: String?=""
                private var mModel: String?=""
                private var mAge: String?=""
                private var mApplicance:
                * */

            }
            else{
                Toast.makeText(this, "Please fill all details!", Toast.LENGTH_LONG).show()
            }
        }

    }
}