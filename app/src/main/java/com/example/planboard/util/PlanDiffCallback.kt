package com.example.planboard.util

import androidx.recyclerview.widget.DiffUtil
import com.example.planboard.ui.plan.room.EntityPlan

class PlanDiffCallback(private val mOldPlanLis: List<EntityPlan>, private val mNewPlanList: List<EntityPlan>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldPlanLis.size
    }

    override fun getNewListSize(): Int {
        return mNewPlanList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldPlanLis[oldItemPosition].id == mNewPlanList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldPlanLis[oldItemPosition]
        val newEmployee = mOldPlanLis[newItemPosition]
        return oldEmployee.title == newEmployee.title && oldEmployee.plan == newEmployee.plan && oldEmployee.date == newEmployee.date
    }

}