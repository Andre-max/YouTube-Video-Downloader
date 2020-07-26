package com.example.youtubedownloader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.youtubedownloader.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.settings_activity)

//        val formatArray = mutableListOf("Audio", "144p", "240p", "360p", "480p", "720p", "1080p")
        val formatArray = mutableListOf("Audio", "Video")
        val preference = MyPreference(applicationContext)
        val spinner = binding.videoQualitySpinner
        val bottomNavigationView = binding.bottomNavView
        var chosenFormat = preference.getStringFormatPref()
        val saveBtn = binding.saveBtn
        val cancelBtn = binding.cancelBtn
        var itemPosition: Int? = null

        fun resetList(){
            formatArray.clear()
            formatArray.addAll(listOf("Audio", "144p", "240p", "360p", "480p", "720p", "1080p"))
        }

        fun changeTopItem() {
            currentChar=""
            formatArray.forEach {
                if (it == chosenFormat) {
                    currentChar = it
                }
            }
            Log.i("SettingsActivity", "currentChar = $currentChar and chosenFormat is $chosenFormat")
            formatArray.remove(currentChar)
            formatArray.add(0, currentChar)
        }

//        changeTopItem()

        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, formatArray)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                itemPosition = position

                saveBtn.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE

//                changeTopItem()
            }
        }

        fun saveItem(position: Int){
            val tag = itagList[position]
            preference.setPreference(tag)

            preference.setStringFormatPref(formatArray[position])
            chosenFormat = preference.getStringFormatPref()
        }

        saveBtn.setOnClickListener {
            saveItem(itemPosition!!)

            saveBtn.visibility = View.GONE
            cancelBtn.visibility = View.GONE
        }

        cancelBtn.setOnClickListener {
            saveBtn.visibility = View.GONE
            cancelBtn.visibility = View.GONE
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.download_id -> {

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)

                    true
                }
                else -> {


                    false
                }
            }
        }

    }
}
