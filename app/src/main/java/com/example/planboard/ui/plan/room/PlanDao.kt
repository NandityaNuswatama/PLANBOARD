package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanDao {
    @Query("SELECT * from plan_table ORDER BY id ASC")
    fun getPlans(): LiveData<List<EntityPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entityPlan: EntityPlan): Long

    @Query("DELETE FROM plan_table  WHERE id = :id")
    fun deleteById(id: Int)

    @Update
    fun update(entityPlan: EntityPlan)

    @Delete
    fun delete(entityPlan: EntityPlan)
}