package com.github.kotvertolet.youtubejextractor

import android.util.Log
import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import com.github.kotvertolet.youtubejextractor.exception.SignatureDecryptionException
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.github.kotvertolet.youtubejextractor.models.subtitles.Subtitle
import com.github.kotvertolet.youtubejextractor.models.youtube.playerConfig.VideoPlayerConfig
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.PlayerResponse
import com.github.kotvertolet.youtubejextractor.models.youtube.videoData.YoutubeVideoData
import com.github.kotvertolet.youtubejextractor.network.GoogleVideoNetwork
import com.github.kotvertolet.youtubejextractor.network.YoutubeNetwork
import com.github.kotvertolet.youtubejextractor.utils.CommonUtils.LogI
import com.github.kotvertolet.youtubejextractor.utils.CommonUtils.matchWithPatterns
import com.github.kotvertolet.youtubejextractor.utils.DecryptionUtils
import com.github.kotvertolet.youtubejextractor.utils.ExtractionUtils
import com.github.kotvertolet.youtubejextractor.utils.StringUtils.splitUrlParams
import com.github.kotvertolet.youtubejextractor.utils.YoutubePlayerUtils
import com.google.code.regexp.Pattern
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.xml.sax.SAXException
import retrofit2.Response
import java.io.IOException
import java.net.URL
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class YoutubeJExtractor {
    private val TAG = javaClass.simpleName
    private val youtubeNetwork: YoutubeNetwork
    private val youtubePlayerUtils: YoutubePlayerUtils
    private val extractionUtils: ExtractionUtils
    private val gson: Gson?
    private var videoPageHtml: String? = null

    /**
     * No-args constructor
     */
    constructor() {
        gson = IGsonFactoryImpl().initGson()
        youtubeNetwork = YoutubeNetwork(gson)
        youtubePlayerUtils = YoutubePlayerUtils(youtubeNetwork)
        extractionUtils = ExtractionUtils(youtubePlayerUtils)
    }

    /**
     * Constructs YoutubeJExtractor with custom OkHttpClient instance which allows for ex.
     * to use custom proxy to deal with region restricted video
     *
     * @param client Custom OkHttpClient instance
     */
    constructor(client: OkHttpClient?) {
        gson = IGsonFactoryImpl().initGson()
        youtubeNetwork = YoutubeNetwork(gson, client)
        youtubePlayerUtils = YoutubePlayerUtils(youtubeNetwork)
        extractionUtils = ExtractionUtils(youtubePlayerUtils)
    }

    @Throws(ExtractionException::class, YoutubeRequestException::class)
    suspend fun extract(videoId: String): YoutubeVideoData {
        return try {
            LogI(TAG, "Extracting video data from youtube page")
            val playerResponse = extractAndPrepareVideoData(videoId)
            YoutubeVideoData(
                playerResponse!!.videoDetails,
                playerResponse.rawStreamingData!!
            )
        } catch (e: SignatureDecryptionException) {
            throw ExtractionException(e)
        }
    }

    suspend fun extract(videoId: String, callback: JExtractorCallback) {
        try {
            val playerResponse = extractAndPrepareVideoData(videoId)
            val youtubeVideoData = YoutubeVideoData(
                playerResponse!!.videoDetails,
                playerResponse.rawStreamingData!!
            )
            callback.onSuccess(youtubeVideoData)
        } catch (e: SignatureDecryptionException) {
            callback.onError(e)
        } catch (e: ExtractionException) {
            callback.onError(e)
        } catch (e: YoutubeRequestException) {
            callback.onNetworkException(e)
        }
    }

    fun extractSubtitles(videoId: String?): Map<String, ArrayList<Subtitle>> {
        val subtitlesLangsResponse: Response<ResponseBody?>
        val googleVideoNetwork = GoogleVideoNetwork(gson)
        try {
            subtitlesLangsResponse = googleVideoNetwork.getSubtitlesList(videoId)
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val languagesXml = builder.parse(
                subtitlesLangsResponse.body()!!.byteStream()
            )
            val languagesNodeList = languagesXml.documentElement.childNodes
            return if (languagesNodeList.length > 0) {
                val availableSubtitlesLangCodes = ArrayList<String>()
                for (i in 0 until languagesNodeList.length) {
                    val langCode =
                        languagesNodeList.item(i).attributes.getNamedItem("lang_code").nodeValue
                    availableSubtitlesLangCodes.add(langCode)
                }
                val subtitlesByLang: MutableMap<String, ArrayList<Subtitle>> = HashMap()
                for (langCode in availableSubtitlesLangCodes) {
                    val response = googleVideoNetwork.getSubtitles(videoId, langCode)
                    val subtitlesXml = builder.parse(response!!.body()!!.byteStream())
                    val subLineNodeList = subtitlesXml.documentElement.childNodes
                    val subtitleArrayList = ArrayList<Subtitle>()
                    for (i in 0 until subLineNodeList.length) {
                        val node = subLineNodeList.item(i)
                        val start = node.attributes.getNamedItem("start").nodeValue
                        val duration = node.attributes.getNamedItem("dur").nodeValue
                        val text = node.textContent
                        subtitleArrayList.add(Subtitle(start, duration, text))
                    }
                    subtitlesByLang[langCode] = subtitleArrayList
                }
                subtitlesByLang
            } else {
                LogI(TAG, "Subtitles not found")
                emptyMap()
            }
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }
        return emptyMap()
    }

    @Throws(
        ExtractionException::class,
        YoutubeRequestException::class,
        SignatureDecryptionException::class
    )
    private suspend fun extractAndPrepareVideoData(videoId: String): PlayerResponse? {
        LogI(TAG, "Extracting video data from youtube page")
        val playerResponse = extractYoutubeVideoData(videoId)
        if (checkIfStreamsAreCiphered(playerResponse)) {
            LogI(TAG, "Streams are ciphered, decrypting")
            decryptYoutubeStreams(playerResponse)
        } else LogI(TAG, "Streams are not encrypted")
        return playerResponse
    }

    @Throws(ExtractionException::class, YoutubeRequestException::class)
    private suspend fun extractYoutubeVideoData(videoId: String): PlayerResponse? {
        val playerResponse: PlayerResponse?
        try {
            val url: URL
            videoPageHtml = youtubeNetwork.getYoutubeVideoPage(videoId)
            //Protocol and domain are necessary to split url params correctly
            val urlProtocolAndDomain = "http://youtube.con/v?"
            if (extractionUtils.isVideoAgeRestricted(videoPageHtml!!)) {
                LogI(TAG, "Age restricted video detected, getting video data from google apis")
                val videoInfo = getVideoInfoForAgeRestrictedVideo(videoId)
                url = URL(urlProtocolAndDomain + videoInfo)
                val videoInfoMap: Map<String, String> = splitUrlParams(url) as Map<String, String>
                val rawPlayerResponse = videoInfoMap["player_response"]
                if (rawPlayerResponse == null || rawPlayerResponse.isEmpty()) {
                    throw ExtractionException("Player response extracted from video info was null or empty")
                }
                playerResponse = gson!!.fromJson(
                    gson.toJson(videoInfoMap["player_response"]),
                    PlayerResponse::class.java
                )
            } else {
                LogI(TAG, "Video is not age restricted, extracting youtube video player config")
                playerResponse = extractYoutubePlayerConfig(videoId).args!!.playerResponse
            }
        } catch (e: IOException) {
            throw ExtractionException(e)
        }
        return playerResponse
    }

    @Throws(ExtractionException::class)
    private fun extractYoutubePlayerConfig(videoId: String): VideoPlayerConfig {
        val patterns = Arrays.asList(
            Pattern.compile(";ytplayer\\.config\\s*=\\s*(\\{.+?\\});ytplayer"),
            Pattern.compile(";ytplayer\\.config\\s*=\\s*(\\{.+?\\});")
        )
        val result = matchWithPatterns(patterns, videoPageHtml)
        return if (result != null) {
            gson!!.fromJson(result, VideoPlayerConfig::class.java)
        } else {
            val videoIsUnavailableMessagePattern =
                Pattern.compile("<h1\\sid=\"unavailable-message\"\\sclass=\"message\">\\n\\s+(.+?)\\n\\s+<\\/h1>")
            val matcher = videoIsUnavailableMessagePattern.matcher(videoPageHtml)
            if (matcher.find()) {
                throw ExtractionException(
                    String.format(
                        "Cannot extract youtube player config, " +
                                "videoId was: %s, reason: %s", videoId, matcher.group(1)
                    )
                )
            } else throw ExtractionException("Cannot extract youtube player config, videoId was: $videoId")
        }
    }

    @Throws(ExtractionException::class)
    private suspend fun getVideoInfoForAgeRestrictedVideo(videoId: String): String {
        return try {
            videoPageHtml = youtubeNetwork.getYoutubeEmbeddedVideoPage(videoId)
            val sts = extractionUtils.extractStsFromVideoPageHtml(videoPageHtml)
            val eUrl = String.format("https://youtube.googleapis.com/v/%s&sts=%s", videoId, sts)
            val videoInfoResponse: String? =
                youtubeNetwork.getYoutubeVideoInfo(videoId, eUrl)

            videoInfoResponse?.let {
                if (it.isEmpty()) throw ExtractionException("Video info was empty") else it
            } ?: throw ExtractionException("Video info response body was null or empty")
        } catch (e: IOException) {
            throw ExtractionException(e)
        } catch (e: NullPointerException) {
            throw ExtractionException(e)
        } catch (e: YoutubeRequestException) {
            throw ExtractionException(e)
        }
    }

    @Throws(ExtractionException::class)
    private fun checkIfStreamsAreCiphered(playerResponse: PlayerResponse?): Boolean {
        // Even if a single stream is encrypted it means they all are
        val rawStreamingData = playerResponse!!.rawStreamingData
        return if (rawStreamingData != null) {
            val formatItems = rawStreamingData.adaptiveStreams
            if (playerResponse.videoDetails!!.isLiveContent) {
                Log.i(TAG, "Requested content is live stream")
                if (formatItems == null || formatItems.size == 0) {
                    Log.i(
                        TAG,
                        "Requested content is a live stream and doesn't contain adaptive streams, " +
                                "use DASH or HLS manifests. If the content is not a live stream or it was but has ended, " +
                                "just wait some time, youtube usually needs a couple of hours to prepare adaptive streams"
                    )
                    return false
                }
            }
            if (formatItems != null && formatItems.size > 0) {
                formatItems[0].cipher != null
            } else throw ExtractionException("AdaptiveFormatItem list was null or empty")
        } else throw ExtractionException("RawStreamingData object was null")
    }

    @Throws(
        ExtractionException::class,
        SignatureDecryptionException::class,
        YoutubeRequestException::class
    )
    private suspend fun decryptYoutubeStreams(youtubeVideoData: PlayerResponse?) {
        val adaptiveStreams = youtubeVideoData!!.rawStreamingData!!.adaptiveStreams
        val muxedStreams = youtubeVideoData.rawStreamingData!!.muxedStreams
        val playerUrl = youtubePlayerUtils.getJsPlayerUrl(videoPageHtml)
        val youtubeVideoPlayerCode = extractionUtils.extractYoutubeVideoPlayerCode(playerUrl)
        val decryptFunctionName = extractionUtils.extractDecryptFunctionName(youtubeVideoPlayerCode)
        val decryptionUtils = DecryptionUtils(youtubeVideoPlayerCode!!, decryptFunctionName)
        for (i in adaptiveStreams!!.indices) {
            val encryptedSignature = adaptiveStreams[i].cipher!!.getS()
            val decryptedSignature = decryptionUtils.decryptSignature(encryptedSignature)
            adaptiveStreams[i].cipher!!.setS(decryptedSignature)
        }
        for (i in muxedStreams!!.indices) {
            val encryptedSignature = muxedStreams[i].cipher!!.getS()
            val decryptedSignature = decryptionUtils.decryptSignature(encryptedSignature)
            muxedStreams[i].cipher!!.setS(decryptedSignature)
        }
    }
}