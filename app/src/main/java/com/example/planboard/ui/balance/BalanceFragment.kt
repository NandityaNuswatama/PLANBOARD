package com.example.planboard.ui.balance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planboard.R
import com.example.planboard.ui.balance.room.EntityBalance
import kotlinx.android.synthetic.main.fragment_balance.*
import kotlinx.android.synthetic.main.item_balance.*

class BalanceFragment : Fragment(), View.OnClickListener {

    private lateinit var balanceViewModel: BalanceViewModel
    private lateinit var balance: List<EntityBalance>
    private lateinit var balanceAdapter: BalanceAdapter
    private val idIncome = 101
    private val idOutcome = 110

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        balanceViewModel = ViewModelProvider(this)[BalanceViewModel::class.java]
        balance = ArrayList()

        showRecyclerView()

        btn_pemasukan.setOnClickListener(this)
        btn_pengeluaran.setOnClickListener(this)
        btn_delete_balance.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_pemasukan -> {
                saveToDatabase(idIncome)
            }
            R.id.btn_pengeluaran -> {
                saveToDatabase(idOutcome)
            }
            R.id.btn_delete_balance -> {

            }
        }
    }

    private fun showRecyclerView(){
        balanceAdapter = BalanceAdapter(balance)
        rv_balance.adapter = balanceAdapter
        rv_balance.layoutManager = LinearLayoutManager(requireContext())
        rv_balance.setHasFixedSize(true)

        observeLiveData()
    }


    private fun observeLiveData(){
        balanceViewModel.getAllBalance().observe(requireActivity(), {balance ->
            balance?.let { balanceAdapter.setBalance(it as ArrayList<EntityBalance>)
            }
        })
    }

    private fun saveToDatabase(id: Int){
        when(id){
            idIncome -> {
                val mBalance = EntityBalance(
                    totalBalance = calculateTotalBalance(id).toString(),
                    nominal = inputNominal.text.toString(),
                    sign = "+",
                    purpose = inputKeperluan.text.toString(),
                    date = inputTanggal.text.toString()
                )
                balanceViewModel.insert(mBalance)
                Log.d("IdBalance: ", mBalance.idBalance.toString())
            }
            idOutcome -> {
                val mBalance = EntityBalance(
                    totalBalance = calculateTotalBalance(id).toString(),
                    nominal = inputNominal.text.toString(),
                    sign = "-",
                    purpose = inputKeperluan.text.toString(),
                    date = inputTanggal.text.toString()
                )
                balanceViewModel.insert(mBalance)
                Log.d("IdBalance: ", mBalance.idBalance.toString())
            }
        }
    }

    private fun calculateTotalBalance(id: Int){
        when(id){
            idIncome -> {
                val inputNominal = inputNominal.text.toString().trim()
                val currentBalance_ = tv_balance.text.toString().trim()
                val balance_ = currentBalance_.toLong() + inputNominal.toLong()
                tv_balance.text = balance_.toString()
            }
            idOutcome -> {
                val inputNominal = inputNominal.text.toString().trim()
                val currentBalance_ = tv_balance.text.toString().trim()
                val balance_ = currentBalance_.toLong() - inputNominal.toLong()
                tv_balance.text = balance_.toString()
            }
        }
    }
}