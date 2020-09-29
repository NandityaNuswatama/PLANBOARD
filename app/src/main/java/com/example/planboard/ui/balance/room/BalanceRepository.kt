package com.example.planboard.ui.balance.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BalanceRepository(private val balanceDao: BalanceDao) {
    val allNominal: LiveData<List<Balance>> = balanceDao.getNominal()

    fun insert(entityBalance: Balance){
       CoroutineScope(Dispatchers.IO).launch{
           balanceDao.insert(entityBalance)
       }
    }

    fun deleteById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            balanceDao.deleteById(id)
        }
    }

    fun getCount(): Int{
        return balanceDao.getCount()
    }

    fun getBalance(id: Int): Long{
        return balanceDao.getBalance(id)
    }
}