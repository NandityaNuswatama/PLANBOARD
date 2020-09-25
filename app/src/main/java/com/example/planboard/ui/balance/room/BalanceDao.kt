package com.example.planboard.ui.balance.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BalanceDao {
    @Query("SELECT * from balance_table ORDER BY idBalance DESC")
    fun getNominal(): LiveData<List<EntityBalance>>

    @Query("SELECT * from balance_table")
    fun getBalance(): LiveData<EntityBalance>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityBalance: EntityBalance): Long

    @Query("DELETE FROM balance_table WHERE idBalance =:id")
    suspend fun deleteById(id: Int)
}