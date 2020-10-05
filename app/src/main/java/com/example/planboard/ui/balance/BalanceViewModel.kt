package com.example.planboard.ui.balance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.planboard.ui.balance.room.Balance
import com.example.planboard.ui.balance.room.BalanceDatabase
import com.example.planboard.ui.balance.room.BalanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BalanceViewModel(application: Application) : AndroidViewModel(application) {
    private val balanceRepository: BalanceRepository
    private val allNominal_: LiveData<List<Balance>>

    init {
        val balanceDao = BalanceDatabase.getDatabase(application).balanceDao()
        balanceRepository = BalanceRepository(balanceDao)
        allNominal_= balanceRepository.allNominal
    }

    fun getAllBalance(): LiveData<List<Balance>>{
        return balanceRepository.allNominal
    }

    fun getCount(): Int{
        return balanceRepository.getCount()
    }

    fun currentBalance(id: Int): Long{
        return balanceRepository.getBalance(id)
    }

    fun insert(entityBalance: Balance) = viewModelScope.launch(Dispatchers.IO){
        balanceRepository.insert(entityBalance)
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO){
        balanceRepository.deleteById(id)
    }
}