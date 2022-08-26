package com.example.task

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Query("Select * from `employee-table` ORDER BY LENGTH(name), name")
    fun fetchAllEmployee():Flow<List<TaskEntity>>

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)


    @Query("Select * from `employee-table` where id=:id")
    fun fetchEmployeeById(id:Int):Flow<TaskEntity>


}