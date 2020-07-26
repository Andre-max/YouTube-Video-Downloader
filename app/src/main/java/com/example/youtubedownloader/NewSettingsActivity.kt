package com.example.youtubedownloader

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.youtubedownloader.database.DataBase
import com.example.youtubedownloader.database.DataClass
import kotlinx.android.synthetic.main.new_settings_fragment.*
import kotlinx.coroutines.*
import timber.log.Timber

class NewSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_settings_fragment)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment(applicationContext))
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        new_bottom_nav_view.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.download_id -> {

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }
                else -> {

                    false
                }
            }
        }
    }
}

class SettingsFragment(globalContext: Context) : PreferenceFragmentCompat() {

    val actualContext = globalContext.applicationContext
    val dao = DataBase.getInstance(actualContext)!!.databaseDao
    val thisJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + thisJob)

    init {
        Timber.i("global is ${globalContext.applicationContext}")
    }

    companion object{
        const val LIST_NAME = "VIDEO_QUALITY"
        const val TAG = "SettingsActivity"
        var databaseItag: Int? = null
    }

    fun getChosenFormat(): Int? {

        val timer = object : CountDownTimer(3200, 400){
            override fun onFinish() {
                uiScope.launch {
                    databaseItag = suspendGetChosenFormat()
                    mainFormat = databaseItag as Int
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                uiScope.launch {
                    databaseItag = suspendGetChosenFormat()
                    mainFormat = databaseItag as Int
                    Timber.i("databaseItag is $databaseItag and mainFormat is $mainFormat")
                }
            }
        }

        timer.start()

        uiScope.launch {
            databaseItag = suspendGetChosenFormat()
            mainFormat = databaseItag as Int
        }

        Timber.i("databaseItag is $databaseItag and mainFormat is $mainFormat")

        if (databaseItag != null) timer.cancel()

        return databaseItag
    }

    private suspend fun suspendGetChosenFormat(): Int{
        return withContext(Dispatchers.IO){
            Timber.i("databaseTag is ${dao.getFormerString()?.stored_format}")
            dao.getFormerString()?.stored_format!!
        }
    }

    fun setChosenFormat(itag: Int){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val dataClass = DataClass(stored_format = itag)
                dao.insertString(dataClass)
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<ListPreference>(LIST_NAME)?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                Timber.i("newValue is $newValue")
                val intValue = newValue.toString().trim().toInt()
                Timber.i("IntValue is $intValue")
                setChosenFormat(intValue)

                return true
            }
        }
    }
}