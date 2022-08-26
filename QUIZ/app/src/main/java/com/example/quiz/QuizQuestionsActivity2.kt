package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import kotlinx.android.synthetic.main.activity_quiz_questions2.*


class QuizQuestionsActivity2 : AppCompatActivity(), View.OnClickListener {

    private var mRating:Int=0
    private var mPos:Int=1
    private var mRestTimer: CountDownTimer?=null
    private var mQuestionTime:Int=0
    private var mCurrentPosition:Int=4
    private var mQuestionsList: ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int=0
    private var mCorrectAnswers:Int=0
    private var mUserName:String?=null
    private var arr=arrayOf(1,1,1,1,1,1,1,1,1,1)
    private var gen:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions2)
        mCurrentPosition=getRandom()
        mUserName=intent.getStringExtra(Constants.USER_NAME)
        gen=intent.getStringExtra(Constants.GENRE)
        mQuestionsList=Constants.getQuestion()
        setQuestion()
        tv_option_one2.setOnClickListener(this)
        tv_option_two2.setOnClickListener(this)
        tv_option_three2.setOnClickListener(this)
        tv_option_four2.setOnClickListener(this)
        btn_submit2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one2 -> {
                selectedOptionView(tv_option_one2, 1)
            }
            R.id.tv_option_two2 -> {
                selectedOptionView(tv_option_two2, 2)
            }
            R.id.tv_option_three2 -> {
                selectedOptionView(tv_option_three2, 3)
            }
            R.id.tv_option_four2 -> {
                selectedOptionView(tv_option_four2, 4)
            }
            R.id.btn_submit2 -> {
                res(mQuestionTime)

            }
        }

    }
    private fun setQuestion(){
        clearTimer()
        setTimer()
        val question=mQuestionsList!!.get(mCurrentPosition)
        defaultOptionsView()

        if (mPos > 5) {
            wrapUp()
        }
        progressBar2.progress=mPos
        tv_progress2.text="${mPos}"+ "/" +"${progressBar2.max}"
        tv_question2.text=question!!.question
        iv_image2.setImageResource(question.image)
        tv_option_one2.text=question.optionOne
        tv_option_two2.text=question.optionTwo
        tv_option_three2.text=question.optionThree
        tv_option_four2.text=question.optionFour
        if(mCurrentPosition==1){
            tv_tag2.text="Category:International"
        }
        else if(mCurrentPosition==1){
            tv_tag2.text="Category:Sports"
        }
        else if(mCurrentPosition==2){
            tv_tag2.text="Category:Airlines"
        }
        else if(mCurrentPosition==3){
            tv_tag2.text="Category:Domestic"
        }
        else if(mCurrentPosition==4){
            tv_tag2.text="Category:Space"
        }
        else if(mCurrentPosition==5){
            tv_tag2.text="Category:Politics"
        }
        else if(mCurrentPosition==6){
            tv_tag2.text="Category:Cricket"
        }
        else if(mCurrentPosition==7){
            tv_tag2.text="Category:Geography"
        }
        else if(mCurrentPosition==8){
            tv_tag2.text="Category:Geography"
        }
        else if(mCurrentPosition==9){
            tv_tag2.text="Category:Geography"
        }

    }
    private fun selectedOptionView(tv:TextView,selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition=selectedOptionNum

        tv.setTextColor(Color.parseColor(("#363A43")))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        //tv.typeface= Typeface.DEFAULT
        tv.background= ContextCompat.getDrawable(
            this, R.drawable.selected_option_border
        )
    }

    private fun defaultOptionsView(){
        val options=ArrayList<TextView>()
        options.add(0,tv_option_one2)
        options.add(1,tv_option_two2)
        options.add(2,tv_option_three2)
        options.add(3,tv_option_four2)
        for(option in options){
            option.setTextColor(Color.parseColor(("#7A8089")))
            //option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this, R.drawable.default_option_back
            )
        }

    }
    private fun answerView(answer:Int, drawableView: Int){
        when (answer){
            1->{
                tv_option_one2.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            2->{
                tv_option_two2.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            3->{
                tv_option_three2.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            4->{
                tv_option_four2.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
        }
    }
    private fun getRandom():Int{
        var x=(0..9).random()//.toInt()
        while(arr[x]!=1){
            x=(0..9).random()
        }
        arr[x]=0
        return x
}

    private fun setTimer(){
        tv_remarks2.visibility=View.INVISIBLE
        flQuesView2.visibility= View.VISIBLE
        btn_submit2.visibility=View.VISIBLE
        mRestTimer=object: CountDownTimer(12000,1000){
            override fun onTick(p0: Long) {
                mQuestionTime++
                tvTimer2.text=(13-mQuestionTime).toString()
                progressBar4.progress=13-mQuestionTime
            }

            override fun onFinish() {
                tvTimer2.text=(3).toString()
                progressBar4.progress=3
                res(mQuestionTime)
                mQuestionTime=0
            }
        }.start()

    }
    private fun clearTimer(){
        if(mRestTimer!=null){
            mRestTimer?.cancel()
            mQuestionTime=0
        }
    }
    private fun res(time:Int){
        tv_remarks2.visibility=View.VISIBLE
        var str:String=""
        if(mSelectedOptionPosition==0){
            flQuesView2.visibility= View.INVISIBLE
            Handler().postDelayed({
                mPos++
                if (mPos>5){
                    wrapUp()
                }
                else{
                    mCurrentPosition=getRandom()
                    setQuestion()
                }
            },2000)
            btn_submit2.visibility=View.INVISIBLE
            val question=mQuestionsList?.get(mCurrentPosition)
            if(question!!.correctAnswer!=mSelectedOptionPosition){
                answerView(mSelectedOptionPosition,R.drawable.wrong_option_border)
                str+="Well Left...\n"
                tv_remarks2.setTextColor(Color.parseColor("#0000ff"))
            }
            else if(mSelectedOptionPosition!=0){
                str+="Well Played!\n"
            }
            answerView(question.correctAnswer,R.drawable.left)
            str+="${question.magic} is the right Answer!"


        }
        else{
            flQuesView2.visibility= View.INVISIBLE
            Handler().postDelayed({
                mPos++
                if (mPos>5){
                    wrapUp()
                }
                else{
                    mCurrentPosition=getRandom()
                    setQuestion()
                }
            },2000)
            btn_submit2.visibility=View.INVISIBLE
            val question=mQuestionsList?.get(mCurrentPosition)
            if(question!!.correctAnswer!=mSelectedOptionPosition){
                answerView(mSelectedOptionPosition,R.drawable.wrong_option_border)
                str+="Oh No! It's Wrong!!\n"
                mRating-=5*time
                tv_remarks2.setTextColor(Color.parseColor("#ff0000"))
            }
            else{
                mCorrectAnswers++
                mRating+=20*(13-time)
                str+="Well Played!\n"
                tv_remarks2.setTextColor(Color.parseColor("#006400"))
            }
            str+="${question.magic} is the right Answer!"

            answerView(question.correctAnswer,R.drawable.correct_option_border)
        }

        tv_remarks2.text=str
        mSelectedOptionPosition=0

    }
    private fun wrapUp(){
        val CorrectAnswers=mCorrectAnswers.toString()
        val Questionslis=5.toString()
        val rr=mRating.toDouble().toString()
        val intent=Intent(this,ResultActivity::class.java)
        intent.putExtra(Constants.RATING,rr)
        intent.putExtra(Constants.USER_NAME,mUserName)
        intent.putExtra(Constants.CORRECT_ANSWERS,CorrectAnswers)
        intent.putExtra(Constants.TOTAL_QUESTIONS,Questionslis)
        intent.putExtra(Constants.GENRE, gen)
        //Toast.makeText(this,"My Rating is ${mRating}", Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        if(mRestTimer!=null){
            mRestTimer?.cancel()
            mQuestionTime=0
        }
    }


    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("END QUIZ")
        builder.setMessage("Are you sure you want to End the Quiz?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            val intent=Intent(this,MainActivity::class.java)
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