package com.example.youtubedownloader

import android.content.Context
import android.util.Log

class MyPreference(context: Context) {

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getStringFormatPref(): String{
        Log.i("MyPref", "preference.getString(PREF_FORMAT, \"144p\") is ${preference.getString(PREF_FORMAT, "144p")}")
        return preference.getString(PREF_FORMAT, "144p") ?: "144p"
    }

    fun setStringFormatPref(format: String){
        val editor = preference.edit()
        editor.putString(PREF_FORMAT, format)
        editor.apply()
    }

    fun getPreference(): Int{
        Log.i("MyPref", "preference.getInt(VIDEO_QUALITY_COUNT, 22) is ${preference.getInt(VIDEO_QUALITY_COUNT, 22)}")
        return preference.getInt(VIDEO_QUALITY_COUNT, 22)
    }

    fun setPreference(count: Int){
        val editor = preference.edit()
        editor.putInt(VIDEO_QUALITY_COUNT, count)
        editor.apply()
    }
}