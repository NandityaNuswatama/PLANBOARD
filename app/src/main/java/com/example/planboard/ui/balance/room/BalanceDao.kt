package com.example.planboard.ui.balance.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BalanceDao {
    @Query("SELECT * from table_balance ORDER BY idBalance DESC")
    fun getNominal(): LiveData<List<Balance>>

    @Query("SELECT totalBalance from table_balance WHERE idBalance =:id")
    fun getBalance(id: Int): Long

    @Query("SELECT COUNT(*) FROM table_balance")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityBalance: Balance): Long

    @Query("DELETE FROM table_balance WHERE idBalance =:id")
    suspend fun deleteById(id: Int)
}