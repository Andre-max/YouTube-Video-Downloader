package com.example.youtubedownloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.youtubedownloader.databinding.ActivityMainBinding
import com.example.youtubedownloader.utils.showShortToast
import com.github.kotvertolet.youtubejextractor.*
import com.github.kotvertolet.youtubejextractor.exception.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private val appFolder = "${Environment.DIRECTORY_DOWNLOADS}/Youtube Downloader"

    private lateinit var binding: ActivityMainBinding
    private var fromAnotherApp = false
    private var isPermissionGranted = false
    private var otherAppUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (Intent.ACTION_SEND == intent.action && intent.type != null && "text/plain" == intent.type) {
            fromAnotherApp = true
            otherAppUrl = intent.getStringExtra(Intent.EXTRA_TEXT)
            Timber.d("Activity called from an intent with data as $otherAppUrl")
            requestPermissionAndGetVideo(otherAppUrl?.getVideoId())
        }

        binding.downloadBtn.setOnClickListener {
            val userInput = binding.enterUrlEdit.text.toString()
            if (userInput.isNotEmpty()) {
                requestPermissionAndGetVideo(userInput.getVideoId())
            } else {
                showShortToast("Enter url first")
            }
        }

    }

    private fun requestPermissionAndGetVideo(videoId: String?) {
        Dexter.withContext(this)
            .withPermissions(listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .withListener(object : BaseMultiplePermissionsListener(){
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(p0)
                    isPermissionGranted = true
                    GlobalScope.launch {
                        getYoutubeVideo(videoId)
                    }
                }
            })
            .check()
    }

    suspend fun getYoutubeVideo(videoId: String?) {
        val youtubeJExtractor = YoutubeJExtractor()
        try {
            val videoData = youtubeJExtractor.extract(videoId ?: return)
            val muxedStreams = videoData.streamingData?.muxedStreams

            val firstMuxedStream = muxedStreams?.get(0)
            val lastMuxedStream = muxedStreams?.last()

            Timber.d("firstMuxedStream is ${firstMuxedStream?.toString()}")
            Timber.d("lastMuxedStream is ${lastMuxedStream?.toString()}")

            binding.enterUrlEdit.setText(firstMuxedStream?.url)
            downloadFromUrl(firstMuxedStream?.url, videoData.videoDetails?.title)
        } catch (e: ExtractionException) {
            // Something really bad happened, nothing we can do except just show some error notification to the user
            showShortToast(e.message.toString())
        } catch (e: YoutubeRequestException) {
            // Possibly there are some connection problems, ask user to check the internet connection and then retry
            showShortToast("Turn on Wi-FI connection")
        }
    }

    @Suppress("DEPRECATION")
    private fun downloadFromUrl(
        url: String?,
        videoTitle: String?
    ) {
        if (url == null) return
        Timber.d("Url is $url, videoTitle is $videoTitle")

        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(videoTitle ?: "Downloading")
        request.setDescription("The file is downloading...")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "$appFolder/${videoTitle ?: UUID.randomUUID()?.toString()}"
        )

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        otherAppUrl = null

        binding.enterUrlEdit.text.clear()
        showShortToast("Download has started successfully")
        if (fromAnotherApp) finish()
    }
}
