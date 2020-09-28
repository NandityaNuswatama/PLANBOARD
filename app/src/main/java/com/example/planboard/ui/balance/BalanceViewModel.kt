package com.example.planboard.ui.balance

import android.app.Application
import android.provider.LiveFolders
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.planboard.ui.balance.room.BalanceDatabase
import com.example.planboard.ui.balance.room.BalanceRepository
import com.example.planboard.ui.balance.room.EntityBalance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BalanceViewModel(application: Application) : AndroidViewModel(application) {
    private val balanceRepository: BalanceRepository
    private val allNominal_: LiveData<List<EntityBalance>>
    private val currentBalance_: LiveData<EntityBalance>

    init {
        val balanceDao = BalanceDatabase.getDatabase(application).balanceDao()
        balanceRepository = BalanceRepository(balanceDao)
        allNominal_= balanceRepository.allNominal
        currentBalance_ = balanceRepository.currentBalance
    }

    fun getAllBalance(): LiveData<List<EntityBalance>>{
        return balanceRepository.allNominal
    }

    fun getCount(): Int{
        return balanceRepository.getCount()
    }

    fun currentBalance(): LiveData<EntityBalance>{
        return balanceRepository.currentBalance
    }

    fun insert(entityBalance: EntityBalance) = viewModelScope.launch(Dispatchers.IO){
        balanceRepository.insert(entityBalance)
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO){
        balanceRepository.deleteById(id)
    }
}