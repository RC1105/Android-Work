package com.example.tictactoe

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.Global.putInt
import android.util.Log
import android.view.View.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.help.*
import kotlinx.android.synthetic.main.settings.*
import kotlinx.android.synthetic.main.winner.*

class MainActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer?=null
    var arr=arrayOf(intArrayOf(0,0,0), intArrayOf(0,0,0), intArrayOf(0,0,0))
    private var mCntO:Int=0
    private var mCntX:Int=0
    private var mToMute:Boolean= true
    private var theBackOfficial:Int=R.drawable.white
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        val i=initTurn()
        var prev=i
        iv_settings.setOnClickListener{
            showSettingLayout()
        }
        tv_help.setOnClickListener {
            showHelpLayout()
        }
        iv_exit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("CLOSING APP")
            builder.setMessage("Are you sure you want to Close the app?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes") { dialogInterface, which ->
                onBackPressed()
            }

            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()

        }
        cell00.setOnClickListener {
            if(arr[0][0]==0) {
                prev = susequentTurn(cell00, prev,0,0)
            }
        }
        cell01.setOnClickListener {
            if(arr[0][1]==0) {
                prev = susequentTurn(cell01, prev,0,1)
            }
        }
        cell02.setOnClickListener {
            if(arr[0][2]==0) {
                prev=susequentTurn(cell02,prev,0,2)
            }
        }
        cell10.setOnClickListener {
            if(arr[1][0]==0) {
                prev=susequentTurn(cell10,prev,1,0)
            }
        }
        cell11.setOnClickListener {
            if(arr[1][1]==0) {
                prev = susequentTurn(cell11, prev,1,1)
            }
        }
        cell12.setOnClickListener {
            if(arr[1][2]==0) {
                prev=susequentTurn(cell12,prev,1,2)

            }
        }
        cell20.setOnClickListener {
            if(arr[2][0]==0) {
                prev=susequentTurn(cell20,prev,2,0)
            }
        }
        cell21.setOnClickListener {
            if(arr[2][1]==0) {
                prev=susequentTurn(cell21,prev,2,1)
            }
        }
        cell22.setOnClickListener {
            if(arr[2][2]==0) {
                prev=susequentTurn(cell22,prev,2,2)
            }
        }
        tv_erase.setOnClickListener {
            resetGame()
            Toast.makeText(this,"Board Reset Successful",Toast.LENGTH_LONG).show()
        }

    }

    private fun resetGame(){
        arr=arrayOf(intArrayOf(0,0,0), intArrayOf(0,0,0), intArrayOf(0,0,0))
        turner.visibility= VISIBLE
        cell00.text=""
        cell01.text=""
        cell02.text=""
        cell10.text=""
        cell11.text=""
        cell12.text=""
        cell20.text=""
        cell21.text=""
        cell22.text=""

        cell00.setBackgroundResource(R.drawable.white)
        cell01.setBackgroundResource(R.drawable.white)
        cell02.setBackgroundResource(R.drawable.white)

        cell10.setBackgroundResource(R.drawable.white)
        cell11.setBackgroundResource(R.drawable.white)
        cell12.setBackgroundResource(R.drawable.white)


        cell20.setBackgroundResource(R.drawable.white)
        cell21.setBackgroundResource(R.drawable.white)
        cell22.setBackgroundResource(R.drawable.white)

    }
    private fun initTurn(): Int {
        val x=(0..1).random()
        if(x==1){
            turner.text ="It's X's Turn"
        }
        else{
            turner.text="It's O's Turn"
        }
        return x
    }
    private fun susequentTurn(cell: TextView, prev:Int, i:Int, j:Int):Int{
        if (prev == 1) {
            cell.text = "X"
            arr[i][j]=1
            cell.setTextColor(Color.parseColor("#ff0000"))

        } else {
            cell.text = "O"
            arr[i][j]=-1
            cell.setTextColor(Color.parseColor("#007FFF"))
        }

        var char:Char=checkWin()

        if(char!='.'){
            winn(char)
        }

        return rev(prev)
    }
    private fun winn(won:Char){
        val winDialog=Dialog(this)
        winDialog.setContentView(R.layout.winner)
        winDialog.setTitle("WINNER")
        Handler().postDelayed({
            winDialog.show()
        },400)
        val editWinner=winDialog.winner
        if(won=='X' || won=='O'){
            editWinner.text="$won Won"
        }
        if(won=='X'){
            editWinner.setTextColor(Color.parseColor("#FF0000"))
            mCntX++
            tv_cntx.text=mCntX.toString()

        }else if(won=='O'){
            editWinner.setTextColor(Color.parseColor("#007FFF"))
            mCntO++
            tv_cnto.text=mCntO.toString()
        }
        else{
            editWinner.setTextColor(Color.parseColor("#00ff00"))
            editWinner.text="It's a DRAW"
        }
        saveData()
        val close=winDialog.tv_close
        close.setOnClickListener {
            winDialog.cancel()
        }
        val play=winDialog.tv_playAgain
        play.setOnClickListener {
            winDialog.cancel()
            resetGame()
        }
    }
    private fun saveData(){
        val ScoreX=mCntX
        val ScoreO=mCntO
        val Mute=mToMute
        val sharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.apply{
            putInt("OScore",ScoreO)
            putInt("XScore",ScoreX)
            putBoolean("toMute",Mute)
            putInt("back",theBackOfficial)
        }.apply()
    }
    private fun loadData(){
        val sharedPreferences=getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val fetchX=sharedPreferences.getInt("XScore",0)
        val fetchO=sharedPreferences.getInt("OScore",0)
        val fetchBack=sharedPreferences.getInt("back",R.drawable.white)
        val mMute=sharedPreferences.getBoolean("toMute",true)
        mToMute=mMute
        if(!mToMute){
            playMusic()
        }
        tv_cnto.text=fetchO.toString()
        tv_cntx.text=fetchX.toString()
        theBackOfficial=fetchBack
        offieBack.setBackgroundResource(theBackOfficial)
        mCntO=fetchO
        mCntX=fetchX

    }
    private fun checkWin():Char{
        var winnerOut:Char= '.'
        for (i in 0..2){
            if(arr[i][1]==1 && arr[i][2]==1 && arr[i][0]==1){
                Toast.makeText(this,"X won",Toast.LENGTH_LONG).show()
                turner.visibility= INVISIBLE
                winnerOut='X'
                shadeIt(0,1,2,i,"row")
                Log.i("yo","${arr[i][0]} ${arr[i][1]}  ${arr[i][2]} ")
            }
            if(arr[1][i]==1 && arr[2][i]==1 && arr[0][i]==1){
                Toast.makeText(this,"X won",Toast.LENGTH_LONG).show()
                turner.visibility= INVISIBLE
                winnerOut='X'
                shadeIt(0,1,2,i,"col")
                Log.i("yo","${arr[0][i]} ${arr[1][i]}  ${arr[2][i]} ")
            }
        }

        for (i in 0..2){
            if(arr[i][1]==-1 && arr[i][2]==-1 && arr[i][0]==-1){
                Toast.makeText(this,"O won",Toast.LENGTH_LONG).show()
                turner.visibility= INVISIBLE
                winnerOut='O'

                shadeIt(0,1,2,i,"row")
                Log.i("yo","${arr[i][0]} ${arr[i][1]}  ${arr[i][2]} ")
            }
            if(arr[1][i]==-1 && arr[2][i]==-1 && arr[0][i]==-1){
                Toast.makeText(this,"O won",Toast.LENGTH_LONG).show()
                turner.visibility= INVISIBLE
                winnerOut='O'

                shadeIt(0,1,2,i,"col")
                Log.i("yo","${arr[i][0]} ${arr[i][1]}  ${arr[i][2]} ")
            }
        }

        if((arr[0][0]==1 && arr[1][1]==1 && arr[2][2]==1) || (arr[0][2]==1 && arr[1][1]==1 && arr[2][0]==1)){
            Toast.makeText(this,"X won",Toast.LENGTH_LONG).show()
            turner.visibility= INVISIBLE
            winnerOut='X'
            if(arr[0][0]==1 && arr[1][1]==1 && arr[2][2]==1)
                shadeIt(0,1,2,1,"dia")
            else{
                shadeIt(0,1,2,-1,"dia")
            }
            Log.i("yo","${arr[0][0]} ${arr[0][1]}  ${arr[0][2]} ")

        }

        if((arr[0][0]==-1 && arr[1][1]==-1 && arr[2][2]==-1) || (arr[0][2]==-1 && arr[1][1]==-1 && arr[2][0]==-1) ){
            Toast.makeText(this,"O won ",Toast.LENGTH_LONG).show()
            turner.visibility= INVISIBLE
            winnerOut='O'
            if(arr[0][0]==-1 && arr[1][1]==-1 && arr[2][2]==-1)
                shadeIt(0,1,2,1,"dia")
            else{
                shadeIt(0,1,2,-1,"dia")
            }
            Log.i("yo","${arr[0][0]} ${arr[0][1]}  ${arr[0][2]} ")

        }
        if(winnerOut=='O'|| winnerOut=='X'){
            arr=arrayOf(intArrayOf(1,1,1), intArrayOf(1,1,1), intArrayOf(1,1,1))
        }
        else{
            if(checkDraw()==true){
                Toast.makeText(this,"DRAW",Toast.LENGTH_LONG).show()
                turner.visibility= INVISIBLE
                winnerOut='D'
            }
        }
        return winnerOut
    }
    private fun shadeIt(x:Int, y:Int, z:Int,w:Int, layo:String){
        if(layo=="row"){
            if(w==1){
                cell10.setBackgroundResource(R.drawable.pinky)
                cell11.setBackgroundResource(R.drawable.pinky)
                cell12.setBackgroundResource(R.drawable.pinky)
            }
            if (w==2){

                cell20.setBackgroundResource(R.drawable.pinky)
                cell21.setBackgroundResource(R.drawable.pinky)
                cell22.setBackgroundResource(R.drawable.pinky)
            }
            if(w==0){
                cell00.setBackgroundResource(R.drawable.pinky)
                cell01.setBackgroundResource(R.drawable.pinky)
                cell02.setBackgroundResource(R.drawable.pinky)

            }
        }
        else if(layo=="col"){
            if(w==1){
                cell01.setBackgroundResource(R.drawable.pinky)
                cell11.setBackgroundResource(R.drawable.pinky)
                cell21.setBackgroundResource(R.drawable.pinky)

            }
            if (w==2){

                cell02.setBackgroundResource(R.drawable.pinky)
                cell12.setBackgroundResource(R.drawable.pinky)
                cell22.setBackgroundResource(R.drawable.pinky)
            }
            if(w==0){
                cell00.setBackgroundResource(R.drawable.pinky)
                cell10.setBackgroundResource(R.drawable.pinky)
                cell20.setBackgroundResource(R.drawable.pinky)
            }
        }
        else if(layo=="dia"){
            if(w==1){
                cell00.setBackgroundResource(R.drawable.pinky)
                cell11.setBackgroundResource(R.drawable.pinky)
                cell22.setBackgroundResource(R.drawable.pinky)
            }
            else if(w==-1){
                cell02.setBackgroundResource(R.drawable.pinky)
                cell11.setBackgroundResource(R.drawable.pinky)
                cell20.setBackgroundResource(R.drawable.pinky)

            }
        }

    }
    private fun checkDraw(): Boolean{
        for(i in 0..2){
            for(j in 0..2){
                if(arr[i][j]==0){
                    return false
                }
            }
        }
        Toast.makeText(this,"DRAW",Toast.LENGTH_LONG).show()

        return true
    }
    private fun rev(n:Int):Int{
        if(n==0){
            turner.text="It's X's turn"

            return 1
        }
        turner.text="It's O's turn"

        return 0

    }
    private fun showHelpLayout(){
        val helpDialog= Dialog(this)
        helpDialog.setContentView(R.layout.help)
        helpDialog.setTitle("Help")
        val adapter=MainAdapter(RulesList.RulesList)
        helpDialog.ruleRV.adapter=adapter
        helpDialog.show()
        val backkie2=helpDialog.tv_back1
        backkie2.setOnClickListener {
            helpDialog.cancel()
        }
    }
    private fun showSettingLayout(){
        val settingDialog=Dialog(this)
        settingDialog.setContentView(R.layout.settings)
        settingDialog.setTitle("Settingz")
        settingDialog.show()
        var resStats=settingDialog.tv_reset
        resStats.setOnClickListener {view->
            mCntX=0
            mCntO=0
            tv_cnto.text=(0).toString()
            tv_cntx.text=(0).toString()
            saveData()
            Snackbar.make(view,"Score Cleared!",Snackbar.LENGTH_LONG).show()

        }
        val backkie=settingDialog.tv_back

        //Back Button
        backkie.setOnClickListener {
            settingDialog.cancel()
        }
        //Background
        val the80s=settingDialog.the80s
        the80s.setOnClickListener {view->
            offieBack.setBackgroundResource(R.drawable.the80s)
            theBackOfficial=R.drawable.the80s
            Snackbar.make(view,"Theme Set to 'THE 80s'",Snackbar.LENGTH_LONG).show()
            saveData()
        }
        val mgc=settingDialog.theMagic
        mgc.setOnClickListener {view->
            offieBack.setBackgroundResource(R.drawable.magic2)
            theBackOfficial=R.drawable.magic2
            Snackbar.make(view,"Theme Set to 'THE magic2'",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val theSun=settingDialog.theSun
        theSun.setOnClickListener {view->
            offieBack.setBackgroundResource(R.drawable.magic)
            theBackOfficial=R.drawable.magic
            Snackbar.make(view,"Theme Set to 'magic'",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val theLeaf=settingDialog.theLeaf
        theLeaf.setOnClickListener {view->
            offieBack.setBackgroundResource(R.drawable.leaf)
            theBackOfficial=R.drawable.leaf
            Snackbar.make(view,"Theme Set to 'THE LEAF'",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val theWhite=settingDialog.theWhite
        theWhite.setOnClickListener {view->
            theBackOfficial=R.drawable.white
            offieBack.setBackgroundResource(R.drawable.white)
            Snackbar.make(view,"Theme Set to WHITE",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val thePinky=settingDialog.sunRise
        thePinky.setOnClickListener {view->
            offieBack.setBackgroundResource(R.drawable.sun)
            theBackOfficial=R.drawable.sun
            Snackbar.make(view,"Theme Set to Sun Rise",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val theReddy=settingDialog.theReddy
        theReddy.setOnClickListener {view->
            theBackOfficial=R.drawable.reddy
            offieBack.setBackgroundResource(R.drawable.reddy)
            Snackbar.make(view,"Theme Set to RED",Snackbar.LENGTH_LONG).show()
            saveData()
        }

        val theBlue=settingDialog.theBlue
        theBlue.setOnClickListener {view->
            theBackOfficial=R.drawable.blue
            saveData()
            offieBack.setBackgroundResource(R.drawable.blue)
            Snackbar.make(view,"Theme Set to BLUE",Snackbar.LENGTH_LONG).show()

        }
        // Music Button
        val musicMute=settingDialog.mute
        val musicUnMute=settingDialog.unmute
        val textMute=settingDialog.tv_mute
        val textUnMute=settingDialog.tv_unmute

        musicMute.setOnClickListener{
            musicMute.visibility=GONE
            textMute.visibility=GONE
            textUnMute.visibility= VISIBLE
            musicUnMute.visibility= VISIBLE
            mToMute=true
            stopMusic()
            saveData()
        }
        musicUnMute.setOnClickListener{
            mToMute=false
            musicMute.visibility= VISIBLE
            textUnMute.visibility=GONE
            textMute.visibility= VISIBLE
            musicUnMute.visibility= GONE
            saveData()
            playMusic()
        }
        if(mToMute){
            musicMute.visibility=GONE
            textMute.visibility=GONE
            textUnMute.visibility= VISIBLE
            musicUnMute.visibility= VISIBLE
            stopMusic()

        }
        else{
            musicMute.visibility= VISIBLE
            textUnMute.visibility=GONE
            textMute.visibility= VISIBLE
            musicUnMute.visibility= GONE
            playMusic()
        }
    }

    private fun playMusic(){
 //       var u= Uri.parse("android.resource://com.example.tictactoe/"+R.raw.aud)

        if(mMediaPlayer==null ){

            mMediaPlayer=MediaPlayer.create(this,R.raw.aud)
            mMediaPlayer!!.isLooping=true
            mMediaPlayer!!.start()
        }
        else{
            mMediaPlayer!!.start()
        }
    }
    private fun stopMusic(){
        if(mMediaPlayer!=null){

            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer=null

        }
    }

    override fun onStop() {
        super.onStop()
        if(mMediaPlayer!=null){
            mMediaPlayer!!.release()
            mMediaPlayer=null
        }
    }


}