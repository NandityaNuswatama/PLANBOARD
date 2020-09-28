package com.example.planboard.ui.plan.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan_table")
data class EntityPlan(
    @PrimaryKey(autoGenerate = true) var id: Int = 1,
    @ColumnInfo(name = "title")var title: String? = null,
    @ColumnInfo(name = "plan")var plan: String? = null,
    @ColumnInfo(name = "date")var date: String? = null
)