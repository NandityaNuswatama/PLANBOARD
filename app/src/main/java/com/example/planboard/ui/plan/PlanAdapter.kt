package com.example.planboard.ui.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planboard.R
import com.example.planboard.ui.plan.room.EntityPlan
import kotlinx.android.synthetic.main.item_grid.view.*

class PlanAdapter(private var plans: List<EntityPlan>): RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(plan: EntityPlan)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class PlanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(entityPlan: EntityPlan){
            with(itemView){
                tv_judul_rencana.text = entityPlan.title
                tv_rencana.text = entityPlan.plan
                tv_tanggal_rencana.text = entityPlan.date
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(entityPlan) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return PlanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        return holder.bind(plans[position])
    }

    internal fun setPlans(entityPlan: ArrayList<EntityPlan>){
        plans = entityPlan
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return plans.size
    }
}