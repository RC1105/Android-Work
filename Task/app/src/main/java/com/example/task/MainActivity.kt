package com.example.task

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.databinding.ActivityMainBinding
import com.example.task.databinding.DialogUpdateBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.AM_PM
import java.util.Calendar.HOUR_OF_DAY
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.hours

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val c=Calendar.getInstance()
        val dateTime=c.time
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = sdf.format(dateTime)
        val sdf2=SimpleDateFormat("HH:mm",Locale.getDefault())
        val sdfh=SimpleDateFormat("HH",Locale.getDefault())
        val hr=sdfh.format(dateTime)
        val time=sdf2.format(dateTime)


        if(hr.toInt() >= 16){
            greet.text="Good Evening!"
        }
        else if(hr.toInt() >=12){
            greet.text="Good Afternoon!"
        }
        else if(hr.toInt() >=4 ){
            greet.text="Good Morning!"
        }
        else{
            greet.text="Good Evening!"
        }


        binding?.greetTime?.text=" $date \t @$time IST\n" //+ "$AM_PM"// + $time"

        val taskDao=(application as TaskApp).db.taskDao()
        binding?.btnAdd?.setOnClickListener {
            addRecord(taskDao)
        }
        lifecycleScope.launch{
            taskDao.fetchAllEmployee().collect {
                val list=ArrayList(it)
                setupListOfDataIntoRecyclerView(list,taskDao)
            }
        }

    }
    fun addRecord(taskDao: TaskDao){
        var name: String= binding?.etName?.text.toString()
        var email: String= binding?.etEmailId?.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty()){
            lifecycleScope.launch{
                taskDao.insert(TaskEntity(name=name,email=email))
                Toast.makeText(applicationContext,"Record Saved ",Toast.LENGTH_LONG).show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        }
        else{
            Toast.makeText(this,"Priority or Task Can't be blank",Toast.LENGTH_LONG).show()
        }

    }
    private fun setupListOfDataIntoRecyclerView(
        taskList:ArrayList<TaskEntity>, taskDao: TaskDao
    ){
        if(taskList.isNotEmpty()){
            val itemAdapter = ItemAdapter(taskList,{updateId ->
                updateRecordDialog(updateId,taskDao)
            }){ deleteId->
                lifecycleScope.launch {
                    taskDao.fetchEmployeeById(deleteId).collect {
                        if (it != null) {
                            deleteRecordAlertDialog(deleteId, taskDao, it)
                        }
                    }
                }

            }
            binding?.rvItemsList?.layoutManager=LinearLayoutManager(this)
            binding?.rvItemsList?.adapter=itemAdapter
            binding?.rvItemsList?.visibility= View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility=View.GONE
            binding?.tabHead?.visibility=View.VISIBLE
        }
        else{
            binding?.rvItemsList?.visibility= View.GONE
            binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE
            binding?.tabHead?.visibility=View.GONE

        }
    }
    fun updateRecordDialog(id:Int,employeeDao: TaskDao)  {
        val updateDialog = Dialog(this, R.style.Theme_AppCompat_Dialog_Alert)
        updateDialog.setCancelable(false)

        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.fetchEmployeeById(id).collect {
                if (it != null) {
                    binding.etUpdateName.setText(it.name)
                    binding.etUpdateEmailId.setText(it.email)
                }
            }
        }
        binding.tvUpdate.setOnClickListener {

            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(TaskEntity(id, name, email))
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG)
                        .show()
                    updateDialog.dismiss() // Dialog will be dismissed
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Email cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }

        updateDialog.show()
    }



    fun deleteRecordAlertDialog(id:Int,employeeDao: TaskDao,employee: TaskEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete record with task name ${employee.email}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                employeeDao.delete(TaskEntity(id))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                dialogInterface.dismiss()
            }

        }


        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
