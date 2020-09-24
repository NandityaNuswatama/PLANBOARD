package com.example.planboard.ui.plan.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanRepository(private val planDao: PlanDao) {

    val allPlans: LiveData<List<EntityPlan>> = planDao.getPlans()

    fun insert(entityPlan: EntityPlan){
        CoroutineScope(Dispatchers.IO).launch {
            planDao.insert(entityPlan)
        }
    }

    fun deleteById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            planDao.deleteById(id)
        }
    }

    fun update(entityPlan: EntityPlan){
        CoroutineScope(Dispatchers.IO).launch {
            planDao.update(entityPlan)
        }
    }

    fun delete(entityPlan: EntityPlan){
        CoroutineScope(Dispatchers.IO).launch {
            planDao.delete(entityPlan)
        }
    }
}