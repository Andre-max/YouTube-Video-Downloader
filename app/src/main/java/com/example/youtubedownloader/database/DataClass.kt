package com.example.youtubedownloader.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val MAIN_VALUE = 1

@Entity(tableName = "dataClass")
data class DataClass(
        @PrimaryKey(autoGenerate = false)
        val name_id: Int = MAIN_VALUE,

        @ColumnInfo
        val stored_format: Int
)
