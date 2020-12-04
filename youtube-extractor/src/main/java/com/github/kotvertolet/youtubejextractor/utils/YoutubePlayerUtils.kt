package com.github.kotvertolet.youtubejextractor.utils

import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.github.kotvertolet.youtubejextractor.network.YoutubeNetwork
import com.google.code.regexp.Pattern
import java.io.IOException
import java.util.*

class YoutubePlayerUtils(private val youtubeNetwork: YoutubeNetwork) {
    /**
     * Extracts url of js player from embedded youtube page
     *
     * @param videoPageHtml youtube page
     * @return returns player url (like this one - 'https://www.youtube.com/yts/jsbin/player-vflkwPKV5/en_US/base.js')
     * @throws ExtractionException if there is no video player url in html code which means that
     * there is some problem with regular expression or html code provided
     */
    @Throws(ExtractionException::class)
    fun getJsPlayerUrl(videoPageHtml: String?): String {
        //Pattern pattern = Pattern.compile("\"assets\":.+?\"js\":\\s*(\"[^\"]+\")");
        val patterns = listOf(
            Pattern.compile("<script[^>]+\\bsrc=(\"[^\"]+\")[^>]+\\bname=[\"']player_ias/base"),
            Pattern.compile("\"jsUrl\"\\s*:\\s*(\"[^\"]+\")"),
            Pattern.compile("\"assets\":.+?\"js\":\\s*(\"[^\"]+\")")
        )
        var jsPlayerUrl = CommonUtils.matchWithPatterns(patterns, videoPageHtml)
        return if (jsPlayerUrl != null) {
            jsPlayerUrl = jsPlayerUrl.replace("\\\\".toRegex(), "")
            // Removing leading and trailing quotes
            jsPlayerUrl.replace("^\"|\"$".toRegex(), "")
        } else throw ExtractionException("No js video player url found")
    }

    /**
     * Downloads JS youtube video player
     *
     * @param playerUrl player url
     * @return js code of the player
     * @throws YoutubeRequestException if the player url is invalid or there is connection problems
     */
    @Throws(YoutubeRequestException::class)
    suspend fun downloadJsPlayer(playerUrl: String?): String? {
        return try {
            youtubeNetwork.downloadWebpage(playerUrl)
        } catch (e: IOException) {
            throw YoutubeRequestException("Error while downloading youtube js video player", e)
        } catch (e: NullPointerException) {
            throw YoutubeRequestException("Error while downloading youtube js video player", e)
        }
    }
}