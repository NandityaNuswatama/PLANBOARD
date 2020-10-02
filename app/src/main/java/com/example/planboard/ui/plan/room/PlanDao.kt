package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanDao {
    @Query("SELECT * from table_plan")
    fun getPlans(): LiveData<List<Plan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plan: Plan): Long

    @Query("DELETE FROM table_plan  WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM table_plan")
    fun getCount(): Int
}