package com.example.planboard.ui.plan

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.planboard.R
import com.example.planboard.ui.plan.room.EntityPlan
import com.example.planboard.util.PlanDiffCallback
import kotlinx.android.synthetic.main.item_grid.view.*

class PlanAdapter internal constructor(private val activity: Activity): RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {
    private val listPlans = ArrayList<EntityPlan>()

    inner class PlanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(entityPlan: EntityPlan){
            with(itemView){
                tv_judul_rencana.text = entityPlan.title
                tv_rencana.text = entityPlan.plan
                tv_tanggal_rencana.text = entityPlan.date
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(PlanEditFragment.id_, entityPlan.id)
                    bundle.putString(PlanEditFragment.title_, entityPlan.title)
                    bundle.putString(PlanEditFragment.plan_, entityPlan.plan)
                    bundle.putString(PlanEditFragment.date_, entityPlan.date)
                    findNavController(itemView).navigate(
                        R.id.action_navigation_dashboard_to_planEditFragment,
                        bundle
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return PlanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        return holder.bind(listPlans[position])
    }

    internal fun setPlans(listPlans: List<EntityPlan>){
        val diffCallback = PlanDiffCallback(this.listPlans, listPlans)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listPlans.clear()
        this.listPlans.addAll(listPlans)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return listPlans.size
    }
}