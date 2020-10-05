package com.example.planboard.ui.balance

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planboard.R
import com.example.planboard.ui.balance.room.Balance
import kotlinx.android.synthetic.main.item_balance.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class BalanceAdapter internal constructor(activity: Activity): RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>() {
    private val nominal_= ArrayList<Balance>()

    inner class BalanceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(entityBalance: Balance){
            val myMoney = moneyFormat(entityBalance.nominal.toLong())
                with(itemView){
                tv_balance_item.text = entityBalance.totalBalance.toString()
                tv_tanda.text = entityBalance.sign
                tv_nominal.text = myMoney
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

    internal fun setBalance(balance: List<Balance>){
        this.nominal_.clear()
        this.nominal_.addAll(balance)
        notifyDataSetChanged()
    }

    private fun moneyFormat(money: Long): String{
        val localeID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localeID)
        return format.format(money)
    }
}