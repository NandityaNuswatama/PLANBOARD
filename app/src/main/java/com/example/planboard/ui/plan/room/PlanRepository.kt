package com.example.planboard.ui.plan.room

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PlanRepository(application: Application) {
    private val mPlanDao: PlanDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PlanDatabase.getDatabase(application)
        mPlanDao = db.planDao()
    }

    fun getAllPlans(): LiveData<List<Plan>> = mPlanDao.getPlans()

    fun getCount(): Int = mPlanDao.getCount()

    fun insert(plan: Plan){
        executorService.execute{ mPlanDao.insert(plan) }
    }

    fun deleteById(id: Int){
        executorService.execute { mPlanDao.deleteById(id) }
    }

}