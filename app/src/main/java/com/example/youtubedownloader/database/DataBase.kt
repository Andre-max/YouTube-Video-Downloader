package com.example.youtubedownloader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DataClass::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase(){

    abstract val databaseDao: DatabaseDao

    companion object{
        @Volatile
        var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase? {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext, DataBase::class.java, "namesDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}