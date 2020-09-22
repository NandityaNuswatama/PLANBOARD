package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanDao {
    @Query("SELECT * from plan_table ORDER BY id ASC")
    fun getPlans(): LiveData<List<EntityPlan>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityPlan: EntityPlan)

    @Query("DELETE FROM plan_table")
    suspend fun deleteAll()
}