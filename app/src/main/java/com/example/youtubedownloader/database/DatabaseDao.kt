package com.example.youtubedownloader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertString(data: DataClass)

    @Query("SELECT * FROM dataClass")
    fun getFormerString(): DataClass?
}
