package com.example.planboard.ui.balance.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_balance")
class Balance (
    @PrimaryKey(autoGenerate = true) var idBalance: Int = 0,
    @ColumnInfo(name = "totalBalance") var totalBalance: Long = 0,
    @ColumnInfo(name = "nominal") var nominal: String = "",
    @ColumnInfo(name = "sign") var sign: String = "",
    @ColumnInfo(name = "purpose") var purpose: String = "",
    @ColumnInfo(name = "date") var date: String = ""
)