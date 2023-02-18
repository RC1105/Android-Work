package com.example.appliancerepair

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    var mProgressDialog: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_noAccount.setOnClickListener {
            val intent=Intent(this,Signup::class.java)
            startActivity(intent)
            finish()
        }
        tv_viaPhone.setOnClickListener {
            val intent=Intent(this,otp::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            showProgressDialog()
            if(etPass.text.toString().length==0 || etEmailId.text.toString().length==0) {
                Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmailId.text.toString(),etPass.text.toString()).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        //Toast.makeText(this,"Sign In was successful! ${FirebaseAuth.getInstance().currentUser.toString()}",Toast.LENGTH_LONG).show()
                        hideProgressDialog()
                        val intent= Intent(this,welcomeActivity::class.java)
                        //intent.putExtra("naam",FirebaseAuth.getInstance().currentUser.toString())
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

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