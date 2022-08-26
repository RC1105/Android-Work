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
import androidx.core.widget.TintableCompoundDrawablesView
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import kotlinx.android.synthetic.main.activity_quiz_questions.progressBar
import kotlinx.android.synthetic.main.activity_quiz_questions2.*
import java.lang.StrictMath.random


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{

    private var mRating:Int=0
    private var mRestTimer:CountDownTimer?=null
    private var mQuestionTime:Int=0
    private var mPos:Int=1
    private var mCurrentPosition:Int=4
    private var mQuestionsList: ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int=0
    private var mCorrectAnswers:Int=0;
    private var mUserName:String?=null
    private var arr=arrayOf(1,1,1,1,1,1,1,1,1,1)
    private var gen:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        mCurrentPosition=getRandom()
        mUserName=intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList=Constants.getQuestions()
        gen=intent.getStringExtra(Constants.GENRE)
        setQuestion()
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

    }
    override fun onClick(v:View?) {
        when(v?.id){
            R.id.tv_option_one->{
                selectedOptionView(tv_option_one,1)
            }
            R.id.tv_option_two->{
                selectedOptionView(tv_option_two,2)
            }
            R.id.tv_option_three->{
                selectedOptionView(tv_option_three,3)
            }
            R.id.tv_option_four->{
                selectedOptionView(tv_option_four,4)
            }
            R.id.btn_submit->{
                res(mQuestionTime)
            }
        }

    }

    private fun setQuestion(){
        clearTimer()
        setTimer()
        val question=mQuestionsList!!.get(mCurrentPosition)
        defaultOptionsView()
        if (mPos >5) {
            wrapUp()
        }
        progressBar.progress=mPos
        tv_progress.text="${mPos}"+ "/" +"${progressBar.max}"
        tv_question.text=question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text=question.optionOne
        tv_option_two.text=question.optionTwo
        tv_option_three.text=question.optionThree
        tv_option_four.text=question.optionFour

    }
    private fun selectedOptionView(tv:TextView,selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition=selectedOptionNum

        tv.setTextColor(Color.parseColor(("#363A43")))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.typeface= Typeface.DEFAULT
        tv.background= ContextCompat.getDrawable(
            this, R.drawable.selected_option_border
        )
    }
    private fun defaultOptionsView(){
        val options=ArrayList<TextView>()
        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2,tv_option_three)
        options.add(3,tv_option_four)
        for (option in options){
            option.setTextColor(Color.parseColor(("#7A8089")))

            option.typeface= Typeface.DEFAULT
            option.background= ContextCompat.getDrawable(
                this, R.drawable.default_option_back
                    )
        }


    }

    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1->{

                tv_option_one.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            2->{
                tv_option_two.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            3->{
                tv_option_three.background=ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            4->{
                tv_option_four.background=ContextCompat.getDrawable(
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
        tv_remarks.visibility=View.INVISIBLE
        flQuesView1.visibility= View.VISIBLE
        btn_submit.visibility=View.VISIBLE
        mRestTimer=object: CountDownTimer(12000,1000){
            override fun onTick(p0: Long) {
                mQuestionTime++
                tvTimer1.text=(13-mQuestionTime).toString()
                progressBar1.progress=13-mQuestionTime
            }

            override fun onFinish() {
                tvTimer1.text=(3).toString()
                progressBar1.progress=3
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
        tv_remarks.visibility=View.VISIBLE
        var str:String=""
        if(mSelectedOptionPosition==0){
            flQuesView1.visibility= View.INVISIBLE
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
            btn_submit.visibility=View.INVISIBLE
            val question=mQuestionsList?.get(mCurrentPosition)
            if(question!!.correctAnswer!=mSelectedOptionPosition){
                answerView(mSelectedOptionPosition,R.drawable.wrong_option_border)
                str+="Well Left...\n"
                tv_remarks.setTextColor(Color.parseColor("#0000ff"))
            }
            else if(mSelectedOptionPosition!=0){
                str+="Well Played!\n"
            }
            answerView(question.correctAnswer,R.drawable.left)
            str+="${question.magic} is the right Answer!"


        }
        else{
            flQuesView1.visibility= View.INVISIBLE
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
            btn_submit.visibility=View.INVISIBLE
            val question=mQuestionsList?.get(mCurrentPosition)
            if(question!!.correctAnswer!=mSelectedOptionPosition){
                answerView(mSelectedOptionPosition,R.drawable.wrong_option_border)
                str+="Oh No! It's Wrong!!\n"
                mRating-=5*time
                tv_remarks.setTextColor(Color.parseColor("#ff0000"))
            }
            else{
                mCorrectAnswers++
                mRating+=20*(13-time)
                str+="Well Played!\n"
                tv_remarks.setTextColor(Color.parseColor("#006400"))
            }
            str+="${question.magic} is the right Answer!"

            answerView(question.correctAnswer,R.drawable.correct_option_border)
        }

        tv_remarks.text=str
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
        //Toast.makeText(this,"My Rating is ${mRating}",Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
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
    override fun onDestroy() {
        super.onDestroy()
        if(mRestTimer!=null){
            mRestTimer?.cancel()
            mQuestionTime=0
        }
    }
}


