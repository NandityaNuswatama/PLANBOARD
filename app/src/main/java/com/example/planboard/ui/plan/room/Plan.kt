package com.example.planboard.ui.plan.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_plan")
data class Plan (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title")var title: String? = null,
    @ColumnInfo(name = "plan")var plan: String? = null,
    @ColumnInfo(name = "date")var date: String? = null,
    @ColumnInfo(name = "urgent")var urgent: Int = 0
)