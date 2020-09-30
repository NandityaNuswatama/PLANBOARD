package com.example.planboard.ui.balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planboard.R
import com.example.planboard.ui.balance.room.Balance
import com.example.planboard.util.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_balance.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class BalanceFragment : Fragment(), View.OnClickListener {

    private lateinit var balanceViewModel: BalanceViewModel
    private lateinit var balance: List<Balance>
    private lateinit var balanceAdapter: BalanceAdapter
    private lateinit var counter: AtomicInteger
    private lateinit var money: AtomicLong
    private lateinit var myCurrentBalance: String
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

        balanceViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application)).get(BalanceViewModel::class.java)
        balance = ArrayList()
        balanceAdapter = BalanceAdapter(balance)
        counter = AtomicInteger()
        money = AtomicLong()

        myCurrentBalance = getCurrentBalance(getTableRow()).toString()
        tv_balance.text = myCurrentBalance

        showRecyclerView()
        observeLiveData()

        btn_pemasukan.setOnClickListener(this)
        btn_pengeluaran.setOnClickListener(this)
        btn_delete_balance.setOnClickListener(this)
        btn_today.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_pemasukan -> {
                saveToDatabase(idIncome)
                getTableRow()
                observeLiveData()
                showRecyclerView()
                clearInput()
                tv_balance.text = getCurrentBalance(getTableRow()).toString()
            }
            R.id.btn_pengeluaran -> {
                saveToDatabase(idOutcome)
                getTableRow()
                observeLiveData()
                showRecyclerView()
                clearInput()
                tv_balance.text = getCurrentBalance(getTableRow()).toString()
            }
            R.id.btn_delete_balance -> {
                deleteFromDatabase()
                getTableRow()
                observeLiveData()
                showRecyclerView()
                tv_balance.text = getCurrentBalance(getTableRow()).toString()
            }
            R.id.btn_today -> {
                inputTanggal.setText(balanceViewModel.getToday())
            }
        }
    }

    private fun showRecyclerView(){
        if (getTableRow() > 0) {
            img_money_tree.visibility = View.GONE
            tv_balance_hint.visibility = View.GONE
            balanceAdapter = BalanceAdapter(balance)
            rv_balance.adapter = balanceAdapter
            rv_balance.layoutManager = LinearLayoutManager(requireContext())
            rv_balance.setHasFixedSize(true)
        }
        else{
            img_money_tree.visibility = View.VISIBLE
            tv_balance_hint.visibility = View .VISIBLE
        }
    }

    private fun observeLiveData(){
        balanceViewModel.getAllBalance().observe(requireActivity(), {balance ->
            balance?.let { balanceAdapter.setBalance(it as ArrayList<Balance>)
            }
        })

    }

    private fun saveToDatabase(id: Int){
        when(id){
            idIncome -> {
                if (inputNominal.text.isNotEmpty() && inputKeperluan.text.isNotEmpty() && inputTanggal.text.isNotEmpty()) {
                    val mBalance = Balance(
                        idBalance = getTableRow() + 1,
                        totalBalance = calculateTotalBalance(id),
                        nominal = inputNominal.text.toString(),
                        sign = "+",
                        purpose = inputKeperluan.text.toString(),
                        date = inputTanggal.text.toString()
                    )
                    balanceViewModel.insert(mBalance)
                    Timber.tag("totalBalance: ").d(mBalance.totalBalance.toString())
                }else{
                    Toast.makeText(requireContext(), "Harap diisi semua", Toast.LENGTH_SHORT).show()
                }
            }
            idOutcome -> {
                if (inputNominal.text.isNotEmpty() && inputKeperluan.text.isNotEmpty() && inputTanggal.text.isNotEmpty()) {
                    val mBalance = Balance(
                        idBalance = getTableRow() + 1,
                        totalBalance = calculateTotalBalance(id),
                        nominal = inputNominal.text.toString(),
                        sign = "-",
                        purpose = inputKeperluan.text.toString(),
                        date = inputTanggal.text.toString()
                    )
                    showRecyclerView()
                    balanceViewModel.insert(mBalance)
                    Timber.tag("totalBalance: ").d(mBalance.totalBalance.toString())
                } else{
                    Toast.makeText(requireContext(), "Harap diisi semua", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun calculateTotalBalance(id: Int): Long{
        var myBalance = 0L
        when(id){
            idIncome -> {
                val inputNominal = inputNominal.text.toString().trim()
                val currentBalance_ = myCurrentBalance
                myBalance = currentBalance_.toLong() + inputNominal.toLong()
            }
            idOutcome -> {
                val inputNominal = inputNominal.text.toString().trim()
                val currentBalance_ = myCurrentBalance
                myBalance = currentBalance_.toLong() - inputNominal.toLong()
            }
        }
        return myBalance
    }

    private fun deleteFromDatabase(){
        getTableRow()
        balanceViewModel.deleteById(getTableRow())
    }

    private fun getTableRow(): Int{
        val t = Thread {
            val count = balanceViewModel.getCount()
            counter.set(count)
        }
        t.start()
        t.join()
        return counter.get()
    }

    private fun getCurrentBalance(id: Int): Long{
        val t = Thread {
            val count = balanceViewModel.currentBalance(id)
            money.set(count)
        }
        t.start()
        t.join()
        return money.get()
    }

    private fun clearInput(){
        inputNominal.setText("")
        inputKeperluan.setText("")
        inputTanggal.setText("")
    }
}