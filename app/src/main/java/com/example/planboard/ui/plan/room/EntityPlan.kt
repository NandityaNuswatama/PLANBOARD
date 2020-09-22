package com.example.planboard.ui.plan.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan_table")
class EntityPlan(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title")var title: String,
    @ColumnInfo(name = "plan")var plan: String,
    @ColumnInfo(name = "date")var date: String? = null
)