package com.example.planboard.ui.plan


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.planboard.ui.plan.room.EntityPlan
import com.example.planboard.ui.plan.room.PlanDatabase
import com.example.planboard.ui.plan.room.PlanRepository
import java.util.function.IntConsumer

class PlanViewModel(application: Application) : ViewModel() {
    private val repository: PlanRepository = PlanRepository(application)

    fun getAllPlans(): LiveData<List<EntityPlan>>{
        return repository.getAllPlans()
    }

    fun insert(entityPlan: EntityPlan) {
        repository.insert(entityPlan)
    }

    fun getCount(): Int{
        return  repository.getCount()
    }

    fun deleteById(id: Int){
        repository.deleteById(id)
    }
}