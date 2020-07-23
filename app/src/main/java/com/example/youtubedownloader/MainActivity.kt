package com.example.youtubedownloader

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.SparseArray
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.youtubedownloader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val EXTERNAL_STORAGE_CODE = 1000
    var fromAnotherApp = false

    private var FULL_URL = ""
    val TAG = "MainActivity"
    private var otherAppUrl = ""

    fun toArrayList(sparseArray: SparseArray<YtFile>){
        sparseArray.forEach { key, value ->
            Log.i(TAG, "Key: $key, Value: $value")
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun getYoutubeDownloadUrl() {

        FULL_URL = if (!fromAnotherApp) binding.enterUrlEdit.text.toString() else otherAppUrl
        Log.i(TAG, "full url is $FULL_URL")

        return object : YouTubeExtractor(applicationContext) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {
                    val preference = MyPreference(applicationContext)
                    userItag = preference.getPreference()
                    val itag = userItag ?: 22
                    Log.i(TAG, "itag is $itag and userItag is $userItag")

                    toArrayList(ytFiles)

                    val ytfile = ytFiles.get(itag)
                    val downloadUrl = ytFiles.get(itag).url
                    val videoTitle = videoMeta?.title
                    var filename = ""

                    videoTitle?.let {
                        filename = if (videoTitle.length > 55) {
                            videoTitle.substring(0, 55) + "." + ytfile.format.ext
                        } else {
                            videoTitle + "." + ytfile.format.ext
                        }
                    }
                    val notAllowedSymbols = "[\\\\><\"|*?%:#/]"
                    filename = filename.filter { char -> !notAllowedSymbols.contains(char) }
                    Log.i(TAG, "filename is $filename")

                    downloadFromUrl(downloadUrl, videoTitle, filename)
                    Log.i(TAG, "url is $downloadUrl")
                } else {
                    // Something went wrong we got no urls. Always check this.
//                    finish()
                    Log.i(TAG, "YtFiles are null")
                    Toast.makeText(applicationContext, "The creator of this video or song does not allow you to download this file.", Toast.LENGTH_SHORT).show()
                    if (fromAnotherApp) finish()
                    return
                }
            }
        }.extract(FULL_URL, true, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val bottomNavigationView = binding.mainBottomNavView

        if (Intent.ACTION_SEND == intent.action && intent.type != null && "text/plain" == intent.type) {
            fromAnotherApp = true
            Log.i(TAG, "Activity called from an intent with data as ${intent.getStringExtra(Intent.EXTRA_TEXT)}")
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                Log.i(TAG, "intent data is not null.")
                otherAppUrl = it
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        EXTERNAL_STORAGE_CODE
                    )
                } else {
                    getYoutubeDownloadUrl()
                }
            } else {
                getYoutubeDownloadUrl()
            }
        }

        binding.downloadBtn.setOnClickListener {
            if (binding.enterUrlEdit.text.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            EXTERNAL_STORAGE_CODE
                        )
                    } else {
                        getYoutubeDownloadUrl()
                    }
                } else {
                    getYoutubeDownloadUrl()
                }
            } else {
                Toast.makeText(applicationContext, "Enter url first", Toast.LENGTH_SHORT).show()
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.download_id -> {

                    false
                }
                else -> {

                    val intent = Intent(applicationContext, SettingsActivity::class.java)
                    startActivity(intent)

                    true
                }
            }
        }
    }

    private fun downloadFromUrl(
        url: String,
        videoTitle: String?,
        filename: String
    ) {
        Log.i(TAG, "downloadFromUrl called and url is $url, videoTitle is $videoTitle and filename is $filename")
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(videoTitle ?: "Downloading")
        request.setDescription("The file is downloading...")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            filename
        )

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

        binding.enterUrlEdit.text.clear()
        Toast.makeText(applicationContext, "Download has started successfully", Toast.LENGTH_SHORT).show()
        if (fromAnotherApp) {finish()}
        fromAnotherApp = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getYoutubeDownloadUrl()
                } else {
                    Toast.makeText(
                        this.applicationContext,
                        "Permission denied!!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
