package com.example.planboard.ui.plan.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Plan::class], version = 1, exportSchema = false)
abstract class PlanDatabase : RoomDatabase() {

    abstract fun planDao(): PlanDao

    companion object{
        @Volatile
        private var INSTANCE: PlanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PlanDatabase{
            if(INSTANCE == null){
                synchronized(PlanDatabase::class.java) {
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                           context.applicationContext,
                            PlanDatabase::class.java,
                        "table_plan"
                        )
                        .build()
                    }
                }
            }
            return INSTANCE as PlanDatabase
        }
    }
}