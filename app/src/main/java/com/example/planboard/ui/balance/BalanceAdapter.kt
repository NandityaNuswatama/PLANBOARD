package com.example.planboard.ui.balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planboard.R
import com.example.planboard.ui.balance.room.EntityBalance
import kotlinx.android.synthetic.main.item_balance.view.*

class BalanceAdapter(private var nominal_: List<EntityBalance>): RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>() {
    inner class BalanceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(entityBalance: EntityBalance){
            with(itemView){
                tv_balance_item.text = entityBalance.totalBalance
                tv_tanda.text = entityBalance.sign
                tv_nominal.text = entityBalance.nominal
                tv_purpose.text = entityBalance.purpose
                tv_date_balance.text = entityBalance.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_balance, parent, false)
        return BalanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        return holder.bind(nominal_[position])
    }

    override fun getItemCount(): Int {
        return nominal_.size
    }

    internal fun setBalance(entityBalance: ArrayList<EntityBalance>){
        nominal_ = entityBalance
        notifyDataSetChanged()
    }
}