package com.example.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee-table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    @ColumnInfo(name = "Name")
    var name:String="",
    @ColumnInfo(name="Email")
    var email:String=""
)
