package com.example.planboard.ui.plan


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.planboard.ui.plan.room.EntityPlan
import com.example.planboard.ui.plan.room.PlanDatabase
import com.example.planboard.ui.plan.room.PlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlanRepository
    private val allPlans: LiveData<List<EntityPlan>>

    init {
        val plansDao = PlanDatabase.getDatabase(application).planDao()
        repository = PlanRepository(plansDao)
        allPlans = repository.allPlans
    }

    fun getAllPlans(): LiveData<List<EntityPlan>>{
        return repository.allPlans
    }

    fun insert(entityPlan: EntityPlan) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entityPlan)
    }

    fun deleteById(id: Int){
        repository.deleteById(id)
    }

    fun delete(entityPlan: EntityPlan){
        repository.delete(entityPlan)
    }

    fun update(entityPlan: EntityPlan){
        repository.update(entityPlan)
    }
}