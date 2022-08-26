package com.example.sketch

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var mImageButtonCurrentPaint :ImageButton?=null
    private var mDialog: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        drawing_view.setSizeForBrush(20.toFloat())
        mImageButtonCurrentPaint=ll_paint_colors[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
        )
        ib_brush.setOnClickListener{
            showBrushSizeChooserDialog()
        }
        ib_undo.setOnClickListener{
            drawing_view.onUndo()
        }
        ib_save.setOnClickListener{
            showProgressDialog()
            if(isReadStorageAllowed()){
                lifecycleScope.launch{
                    val myBitmap:Bitmap=getBitmapFromView(fl_drawing_view_container)
                    saveBitmapFile(myBitmap)
                }
            }
        }
        ib_gallery.setOnClickListener{
            if(isReadStorageAllowed()){
                val pickPhotoDialog=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoDialog,GALLERY)
            }
            else{
                requestStoragePermission()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode== GALLERY){
                try{
                    if(data!!.data!=null){
                        iv_background.visibility=View.VISIBLE
                        iv_background.setImageURI(data.data)
                    }
                }catch(e: Exception){
                    e.printStackTrace()
                }

            }
        }
    }
    private fun showBrushSizeChooserDialog(){
        val brushDialog= Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")
        val smallbtn=brushDialog.ib_small_brush
        smallbtn.setOnClickListener {
            drawing_view.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val medbtn=brushDialog.ib_medium_brush
        medbtn.setOnClickListener {
            drawing_view.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largebtn=brushDialog.ib_large_brush
        largebtn.setOnClickListener {
            drawing_view.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }
    fun paintClicked(view: View){
        if(view!==mImageButtonCurrentPaint){
            val imageButton=view as ImageButton
            val colorTag=imageButton.tag.toString()
            drawing_view.setColor(colorTag)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
            )
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_normal)
            )
            mImageButtonCurrentPaint=view
        }
    }/*
    //works only without bg image hence it's useless now :(
    fun erase(view: View){
        val brushDialog= Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")
        val smallbtn=brushDialog.ib_small_brush
        smallbtn.setOnClickListener {
            drawing_view.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val medbtn=brushDialog.ib_medium_brush
        medbtn.setOnClickListener {
            drawing_view.setSizeForBrush(35.toFloat())
            brushDialog.dismiss()
        }
        val largebtn=brushDialog.ib_large_brush
        largebtn.setOnClickListener {
            drawing_view.setSizeForBrush(50.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
        val imageButton=view as ImageButton
        val colorTag=imageButton.tag.toString()
        drawing_view.setColor(colorTag)
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_normal)
        )
    }
    private fun isReadStorageAllowed(): Boolean{
        val result= ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }*/
    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
            Toast.makeText(this,"Need permission to add background",Toast.LENGTH_LONG).show()

        }
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Storage is all set to be accessed",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Permission to access the gallery was denied",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isReadStorageAllowed():Boolean{
        val result=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }


    private fun getBitmapFromView(view: View):Bitmap{
        val returnedBitmap=Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val canvas= Canvas(returnedBitmap)
        val bgDrawable=view.background
        if(bgDrawable!=null){
            bgDrawable.draw(canvas)
        }else{
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    private suspend fun saveBitmapFile(mBitmap: Bitmap?): String{
        var result=""
        withContext(Dispatchers.IO){
            if(mBitmap!=null){
                try{
                    val bytes=ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG,90,bytes)
                    val f=File(externalCacheDir?.absoluteFile.toString()+ File.separator+"Sketch"+ System.currentTimeMillis()/1000+".png")
                    val fo=FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                    fo.close()
                    result=f.absolutePath
                    runOnUiThread{
                        cancelProgressDialog()
                        if(result.isNotEmpty()){
                            Toast.makeText(this@MainActivity,"File saved successfully: $result",Toast.LENGTH_LONG).show()
                            shareImage(result)
                        }else{
                            Toast.makeText(this@MainActivity,"Something went wrong",Toast.LENGTH_LONG).show()
                        }
                    }
                }catch(e:Exception){
                    result=""
                    e.printStackTrace()
                }
            }
        }
        return result
    }

    private fun showProgressDialog(){
        mDialog= Dialog(this)
        mDialog!!.setContentView(R.layout.plz_activity)
        mDialog!!.show()
    }
    private fun cancelProgressDialog(){
        if(mDialog!=null){
            mDialog?.dismiss()
            mDialog=null
        }
    }
    private fun shareImage(result: String){
        MediaScannerConnection.scanFile(this, arrayOf(result),null){
            path,url->
            val shareIntent=Intent()
            shareIntent.action=Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM,url)
            shareIntent.type="image/png"
            startActivity(Intent.createChooser(shareIntent,"Share"));
            }
    }
    companion object{
        val STORAGE_PERMISSION_CODE=1
        val GALLERY=2
    }
}