package com.example.youtubedownloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.andre_max.youtube_url_extractor.ExtractorException
import com.andre_max.youtube_url_extractor.YoutubeStreamExtractor
import com.andre_max.youtube_url_extractor.model.YTMedia
import com.andre_max.youtube_url_extractor.model.YTSubtitles
import com.andre_max.youtube_url_extractor.model.YoutubeMeta
import com.example.youtubedownloader.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val EXTERNAL_STORAGE_CODE = 1000
    var fromAnotherApp = false

    var newDownloadUrl: String? = null

    private var FULL_URL = ""
    val TAG = "MainActivity"
    private var otherAppUrl = ""


    private fun newDownload(){
//        val preference = MyPreference(applicationContext)

        val settingsFragment = SettingsFragment(applicationContext)

        FULL_URL = if (!fromAnotherApp) binding.enterUrlEdit.text.toString() else otherAppUrl
        Timber.i("full url is $FULL_URL")
        val urlList = ArrayList<String>()
        var filename: String
        var videoData: YoutubeMeta?
        val itag = settingsFragment.getChosenFormat()

        val streamExtractorListener = object : YoutubeStreamExtractor.ExtractorListner {
            override fun onExtractionGoesWrong(p0: ExtractorException?) {
                Timber.i("$p0")
                Toast.makeText(applicationContext, "$p0", Toast.LENGTH_SHORT).show()
            }

            override fun onExtractionDone(
                p0: MutableList<YTMedia>?,
                p1: MutableList<YTMedia>?,
                p2: MutableList<YTSubtitles>?,
                p3: YoutubeMeta?
            ) {
                val subUrl = p2?.get(0)?.baseUrl
                Timber.i("subUrl is $subUrl")
                if (p0 != null) {
                    for (adaptiveMedia in p0) {
                        urlList.add(adaptiveMedia.url)
                    }
                }
                if (p1 != null) {
                    for (muxedMedia in p1) {
                        urlList.add(muxedMedia.url)
                    }
                }

                urlList.forEach { Timber.i( "Url: $it") }

                if (p0 == null) Timber.i("p0 is null")
                if (p1 == null) Timber.i("p1 is null")

                newDownloadUrl = p1?.get(0)?.url
                videoData = p3
                Timber.i( "itag is $mainFormat")
                urlList.forEach { eachUrl ->
                    if(eachUrl.contains("itag=$mainFormat")){
                        Timber.i("Found url with itag: $eachUrl")
                        newDownloadUrl = eachUrl
                    }
                }

                val notAllowedSymbols = "[\\\\><\"|*?%:#/]"
                filename = videoData?.title ?: "Downloading"
                filename = filename.filter { char -> !notAllowedSymbols.contains(char) }
                Timber.i( "filename is $filename")

                Timber.i("newDownloadUrl is $newDownloadUrl")
                downloadFromUrl(newDownloadUrl, videoData?.title, filename)
            }
        }

        val streamExtractor = YoutubeStreamExtractor(streamExtractorListener)
        streamExtractor.useDefaultLogin().Extract(FULL_URL)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val bottomNavigationView = binding.mainBottomNavView

        val settingsFragment = SettingsFragment(applicationContext)
        settingsFragment.getChosenFormat()
        Timber.i("settingsFragment is $mainFormat")

        if (Intent.ACTION_SEND == intent.action && intent.type != null && "text/plain" == intent.type) {
            fromAnotherApp = true
            Timber.i("Activity called from an intent with data as ${intent.getStringExtra(Intent.EXTRA_TEXT)}")
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                Timber.i("intent data is not null.")
                otherAppUrl = it
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        EXTERNAL_STORAGE_CODE
                    )
                } else {
                    newDownload()
                }
            } else {
                newDownload()
            }
        }

        val timer = object : CountDownTimer(2000, 500){
            override fun onFinish() {
                settingsFragment.getChosenFormat()
                Timber.i("settingsFragment is $mainFormat")
            }

            override fun onTick(millisUntilFinished: Long) {
                settingsFragment.getChosenFormat()
                Timber.i("settingsFragment is $mainFormat")
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
                        newDownload()
                    }
                } else {
                    newDownload()
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

                    val intent = Intent(applicationContext, NewSettingsActivity::class.java)
                    startActivity(intent)

                    true
                }
            }
        }
    }

    private fun downloadFromUrl(
        url: String?,
        videoTitle: String?,
        filename: String
    ) {
        Timber.i("downloadFromUrl called and url is $url, videoTitle is $videoTitle and filename is $filename")
        Timber.i( "url is null is ${url == null}")
        url?.let {
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
        }

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
                    newDownload()
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
