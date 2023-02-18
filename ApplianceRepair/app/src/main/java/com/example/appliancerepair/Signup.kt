package com.example.appliancerepair

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.etEmailId
import kotlinx.android.synthetic.main.activity_signup.etPass
import kotlinx.android.synthetic.main.progress.*

class Signup : AppCompatActivity() {
    var mProgressDialog:Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signup.setOnClickListener {
            showProgressDialog()
            if(etName.text.toString().length==0 || etEmailId.text.toString().length==0 || etPass.text.toString().length==0 || etMobNo.text.toString().length==0) {
                Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_LONG).show()
            }
            else if(etConfPass.text.toString()!=etPass.text.toString()){
                Toast.makeText(this,"${etConfPass.text} and ${etPass.text}",Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Confirm password doesn't match", Toast.LENGTH_LONG).show()
            }
            else if(etMobNo.text.toString().length!=10){
                Toast.makeText(this,"Invalid Mobile Number", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"All set to Sign Up",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmailId.text.toString(),etPass.text.toString()).addOnCompleteListener{
                    task->
                    if(task.isSuccessful){
                        val firebaseUser : FirebaseUser =task.result!!.user!!
                        val registeredEmail=firebaseUser.email
                        val registeredName=firebaseUser.displayName
                        Toast.makeText(this,"RCXI Welcomes You!! $registeredName",Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        hideProgressDialog()
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Couldn't create an account",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    fun showProgressDialog() {
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog!!.setContentView(R.layout.progress)

        //Start the dialog and display it on screen.
        mProgressDialog!!.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    fun hideProgressDialog() {
        mProgressDialog!!.dismiss()
    }
}