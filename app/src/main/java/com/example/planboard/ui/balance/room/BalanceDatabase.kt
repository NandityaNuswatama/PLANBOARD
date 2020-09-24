package com.example.planboard.ui.balance.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityBalance::class], version = 1, exportSchema = false)
abstract class BalanceDatabase: RoomDatabase() {
        abstract fun balanceDao(): BalanceDao

    companion object{
        @Volatile
        private var INSTANCE: BalanceDatabase? = null

        fun getDatabase(context: Context): BalanceDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, BalanceDatabase::class.java, "balance_database")
                    .build()
            }
            return INSTANCE as BalanceDatabase
        }
    }
}