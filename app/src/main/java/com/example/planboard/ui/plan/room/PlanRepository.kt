package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData

class PlanRepository(private val planDao: PlanDao) {

    val allPlans: LiveData<List<EntityPlan>> = planDao.getPlans()

    suspend fun insert(entityPlan: EntityPlan){
        planDao.insert(entityPlan)
    }
}