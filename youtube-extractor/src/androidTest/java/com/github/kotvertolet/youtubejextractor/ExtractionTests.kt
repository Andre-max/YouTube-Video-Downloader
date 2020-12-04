package com.github.kotvertolet.youtubejextractor

import android.os.Bundle
import androidx.test.runner.AndroidJUnit4
import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.github.kotvertolet.youtubejextractor.models.subtitles.Subtitle
import com.github.kotvertolet.youtubejextractor.models.youtube.videoData.YoutubeVideoData
import com.github.kotvertolet.youtubejextractor.network.YoutubeNetwork
import com.google.gson.GsonBuilder
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExtractionTests : TestCase() {
    private val youtubeJExtractor = YoutubeJExtractor()
    private val youtubeNetwork = YoutubeNetwork(GsonBuilder().create())
    private var videoData: YoutubeVideoData? = null



    @Test(expected = ExtractionException::class)
    @Throws(YoutubeRequestException::class, ExtractionException::class)
    fun checkInvalidVideoId() = runBlockingTest {
        youtubeJExtractor.extract("invalid_id")
    }

    @Test
    @Throws(YoutubeRequestException::class, ExtractionException::class)
    fun checkVideoDataParcel() = runBlockingTest {
        val parcelKey = "parcel_key1"
        videoData = youtubeJExtractor.extract("rkas-NHQnsI")
        val bundle = Bundle()
        bundle.putParcelable(parcelKey, videoData)
        assertEquals(videoData, bundle.getParcelable(parcelKey))
    }

    @Test
    @Throws(ExtractionException::class, YoutubeRequestException::class)
    fun checkVideoWithEncryptedSignature() = runBlockingTest {
        videoData = youtubeJExtractor.extract("xRioA3a6qgg")
        checkIfStreamsWork(videoData)
    }

    @Test
    @Throws(ExtractionException::class, YoutubeRequestException::class)
    fun checkVideoWithoutEncryptedSignature() = runBlockingTest {
        videoData = youtubeJExtractor.extract("jNQXAC9IVRw")
        checkIfStreamsWork(videoData)
    }

    @Test
    @Throws(ExtractionException::class, YoutubeRequestException::class)
    fun checkVideoWithAgeCheck() = runBlockingTest {
        videoData = youtubeJExtractor.extract("Pk0z3Aj3P5E")
        checkIfStreamsWork(videoData)
    }

    @Test
    @Throws(ExtractionException::class, YoutubeRequestException::class)
    fun checkVeryLongVideo() = runBlockingTest {
        videoData = youtubeJExtractor.extract("85bkCmaOh4o")
        checkIfStreamsWork(videoData)
    }

    @Test
    @Throws(ExtractionException::class, YoutubeRequestException::class)
    fun checkVideoWithRestrictedEmbedding() = runBlockingTest {
        videoData = youtubeJExtractor.extract("XcicOBS9mBU")
        checkIfStreamsWork(videoData)
    }

    @Test
    @Throws(YoutubeRequestException::class, ExtractionException::class)
    fun checkLiveStream() = runBlockingTest {
        videoData = youtubeJExtractor.extract("5qap5aO4i9A")
        assertTrue(videoData!!.videoDetails!!.isLiveContent)
        assertNotNull(videoData!!.streamingData!!.dashManifestUrl)
        assertNotNull(videoData!!.streamingData!!.hlsManifestUrl)
        checkIfStreamsWork(videoData)
    }

    //@Test
    @Throws(YoutubeRequestException::class, ExtractionException::class)
    fun checkLiveStreamWithoutAdaptiveStreams() = runBlockingTest {
        videoData = youtubeJExtractor.extract("up0fWFqgC6g")
        assertTrue(videoData!!.videoDetails!!.isLiveContent)
        assertNotNull(videoData!!.streamingData!!.dashManifestUrl)
        assertNotNull(videoData!!.streamingData!!.hlsManifestUrl)
        assertEquals(0, videoData!!.streamingData!!.adaptiveAudioStreams!!.size)
        assertEquals(0, videoData!!.streamingData!!.adaptiveVideoStreams!!.size)
    }

    @Test
    @Throws(YoutubeRequestException::class, ExtractionException::class)
    fun checkMuxedStreamNonEncrypted() = runBlockingTest {
        videoData = youtubeJExtractor.extract("8QyDmvuts9s")
        checkIfStreamsWork(videoData)
    }

    @Test
    fun checkCallbackBasedExtractionSuccessful() = runBlockingTest {
        youtubeJExtractor.extract("iIKxyDRjecU", object : JExtractorCallback {
            override fun onSuccess(videoData: YoutubeVideoData?) = runBlockingTest {
                checkIfStreamsWork(videoData)
            }

            override fun onNetworkException(e: YoutubeRequestException?) {
                fail("Network exception occurred")
            }

            override fun onError(exception: Exception?) {
                fail("Extraction exception occurred")
            }
        })
    }

    @Test
    fun testSubtitlesExtraction() = runBlockingTest {
        val subs = youtubeJExtractor.extractSubtitles("lT3vGaOLWqE")
        assertEquals(8, subs.size)
        assertTrue(subs.containsKey("en"))
        val englishSubs: ArrayList<Subtitle>? = subs["en"]
        assertEquals(208, englishSubs?.size)
        val actualFirstLine = englishSubs?.get(0)
        val expectedFirstLine = Subtitle("0.78", "0.56", "Hi. Shh.")
        val actualLasLine = englishSubs?.get(207)
        val expectedLastLine = Subtitle("620.22", "1.28", "#{{beatboxing}}#")
        assertEquals(expectedFirstLine, actualFirstLine)
        assertEquals(expectedLastLine, actualLasLine)
    }

    private suspend fun checkIfStreamsWork(videoData: YoutubeVideoData?) {
        val streamErrorMask = "Stream wasn't processed correctly, stream details:\\n %s"
        var responseBody: String?
        try {
            if (videoData!!.videoDetails!!.isLiveContent) {
                responseBody = youtubeNetwork.getStream(videoData.streamingData!!.dashManifestUrl)
                assertNotNull(responseBody)
                responseBody = youtubeNetwork.getStream(videoData.streamingData!!.hlsManifestUrl)
                assertNotNull(responseBody)
            } else {
                for (adaptiveVideoStream in videoData.streamingData!!.adaptiveVideoStreams!!) {
                    responseBody = youtubeNetwork.getStream(adaptiveVideoStream!!.url)
                    Assert.assertThat<String?>(
                        String.format(
                            streamErrorMask,
                            adaptiveVideoStream.toString()
                        ), responseBody, Matchers.`is`(Matchers.not(Matchers.nullValue()))
                    )
                    Assert.assertThat(
                        String.format(
                            streamErrorMask,
                            adaptiveVideoStream.toString()
                        ), responseBody, Matchers.`is`(true)
                    )
                }
                for (adaptiveAudioStream in videoData.streamingData!!.adaptiveAudioStreams!!) {
                    responseBody = youtubeNetwork.getStream(adaptiveAudioStream!!.url)
                    Assert.assertThat<String?>(
                        String.format(
                            streamErrorMask,
                            adaptiveAudioStream.toString()
                        ), responseBody, Matchers.`is`(Matchers.not(Matchers.nullValue()))
                    )
                    Assert.assertThat(
                        String.format(
                            streamErrorMask,
                            adaptiveAudioStream.toString()
                        ), responseBody, Matchers.`is`(true)
                    )
                }
                for (muxedStream in videoData.streamingData!!.muxedStreams!!) {
                    responseBody = youtubeNetwork.getStream(muxedStream!!.url)
                    Assert.assertThat<String?>(
                        String.format(
                            streamErrorMask,
                            muxedStream.toString()
                        ), responseBody, Matchers.`is`(Matchers.not(Matchers.nullValue()))
                    )
                    Assert.assertThat(
                        String.format(streamErrorMask, muxedStream.toString()),
                        responseBody,
                        Matchers.`is`(true)
                    )
                }
            }
        } catch (e: YoutubeRequestException) {
            fail(e.message)
        }
    }
}