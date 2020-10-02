package com.example.planboard.ui.plan


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.planboard.ui.plan.room.Plan
import com.example.planboard.ui.plan.room.PlanRepository

class PlanViewModel(application: Application) : ViewModel() {
    private val repository: PlanRepository = PlanRepository(application)

    fun getAllPlans(): LiveData<List<Plan>>{
        return repository.getAllPlans()
    }

    fun insert(plan: Plan) {
        repository.insert(plan)
    }

    fun getCount(): Int{
        return  repository.getCount()
    }

    fun deleteById(id: Int){
        repository.deleteById(id)
    }
}