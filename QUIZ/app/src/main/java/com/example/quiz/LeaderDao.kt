package com.example.quiz

import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)
    //retain suspend and change versions
    // av: 1.4.0 and rv: 2.4.1
    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Query("delete from `employee-table`")
    suspend fun delete()

    @Query("Select * from `employee-table` order by LENGTH(`email-id`) DESC, `email-id` DESC")
    fun fetchAllEmployee():Flow<List<EmployeeEntity>>

    @Query("Select * from `employee-table` where id=:id")
    fun fetchEmployeeById(id:Int):Flow<EmployeeEntity>
}