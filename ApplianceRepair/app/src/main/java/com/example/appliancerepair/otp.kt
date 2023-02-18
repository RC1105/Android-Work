package com.example.appliancerepair

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.api.AuthProvider.newBuilder
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_otp.*
import java.nio.file.attribute.AclEntry.newBuilder
import java.util.concurrent.TimeUnit

class otp : AppCompatActivity() {
    var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
                //signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(baseContext,"Oops",Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {


                    Toast.makeText(baseContext,"Oops",Toast.LENGTH_LONG).show()// The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                var intent=Intent(this@otp,verifyOTP::class.java)
                //val intent= Intent(verifyOTP::class.java)
                val txx= input_mobile_number.text.toString()
                intent.putExtra("mobNo",txx)
                intent.putExtra("backendotp",verificationId)

                startActivity(intent)
                finish()
                // Save verification ID and resending token so we can use them later
                var storedVerificationId = verificationId
                var resendToken = token
            }
        }
        tv_sendOTP.setOnClickListener {

            if(isValidd()){
                pb.visibility= View.VISIBLE
                tv_sendOTP.text="Please Wait"
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91 "+input_mobile_number.text.toString())       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

                /*
                val options=PhoneAuthProvider.newBuilder(auth)
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91"+input_mobile_number.text.toString(),
                    60,
                    TimeUnit.SECONDS,
                    otp.this,
                    PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        publi
                    }

                )
                */
            }
            else{
                Toast.makeText(this,"Invalid Number",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun isValidd(): Boolean {
        var k=input_mobile_number.text.toString()
        val sii=k.length
        if (sii==10){
            return true
        }
        Toast.makeText(this,"Incorrect Mobile number",Toast.LENGTH_LONG).show()
        return false
    }


}