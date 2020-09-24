package com.example.planboard.ui.plan.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityPlan::class], version = 1, exportSchema = false)
abstract class PlanDatabase : RoomDatabase() {

    abstract fun planDao(): PlanDao

    companion object{
        @Volatile
        private var INSTANCE: PlanDatabase? = null

        fun getDatabase(context: Context): PlanDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, PlanDatabase::class.java, "plan_table")
                    .build()
            }
            return INSTANCE as PlanDatabase
        }
    }
}