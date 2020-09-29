package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanDao {
    @Query("SELECT * from plan_table")
    fun getPlans(): LiveData<List<EntityPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entityPlan: EntityPlan): Long

    @Query("DELETE FROM plan_table  WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM plan_table")
    fun getCount(): Int

    @Update
    fun update(entityPlan: EntityPlan)
}