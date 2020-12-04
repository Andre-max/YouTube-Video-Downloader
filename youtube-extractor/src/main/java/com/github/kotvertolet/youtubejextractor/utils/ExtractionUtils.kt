package com.github.kotvertolet.youtubejextractor.utils

import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import com.github.kotvertolet.youtubejextractor.exception.SignatureDecryptionException
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.google.code.regexp.Pattern
import java.util.*

class ExtractionUtils(private val youtubePlayerUtils: YoutubePlayerUtils) {
    fun isVideoAgeRestricted(videoPageHtml: String): Boolean {
        return videoPageHtml.contains("LOGIN_REQUIRED")
    }

    fun extractStsFromVideoPageHtml(embeddedVideoPageHtml: String?): String {
        val pattern = Pattern.compile("sts\"\\s*:\\s*(\\d+)")
        val matcher = pattern.matcher(embeddedVideoPageHtml)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            CommonUtils.LogI(
                ExtractionUtils::class.java.simpleName,
                "Sts param wasn't found in the embedded player webpage code"
            )
            ""
        }
    }

    @Throws(
        YoutubeRequestException::class,
        ExtractionException::class,
        SignatureDecryptionException::class
    )
    suspend fun extractYoutubeVideoPlayerCode(playerUrl: String): String? {
        var innerPlayerUrl = playerUrl
        innerPlayerUrl = preparePlayerUrl(innerPlayerUrl)
        val pattern = Pattern.compile("([a-z]+)$")
        val matcher = pattern.matcher(innerPlayerUrl)
        if (!matcher.find()) {
            throw ExtractionException("Cannot identify player type by url: $innerPlayerUrl")
        }
        return when (val playerType = matcher.group()) {
            "js" -> youtubePlayerUtils.downloadJsPlayer(innerPlayerUrl)
            "swf" -> throw UnsupportedOperationException("Swf player type is not supported")
            else -> throw UnsupportedOperationException("Invalid player type: $playerType")
        }
    }

    @Throws(ExtractionException::class)
    fun extractDecryptFunctionName(playerCode: String?): String {
        val newPattern1 =
            Pattern.compile("\\b\\[cs\\]\\s*&&\\s*[adf]\\.set\\([^,]+\\s*,\\s*encodeURIComponent\\s*\\(\\s*(?<sig>[a-zA-Z0-9$]+)\\(")
        val newPattern2 =
            Pattern.compile("\\b[a-zA-Z0-9]+\\s*&&\\s*[a-zA-Z0-9]+\\.set\\([^,]+\\s*,\\s*encodeURIComponent\\s*\\(\\s*(?<sig>[a-zA-Z0-9$]+)\\(")
        val newPattern3 =
            Pattern.compile("(?:\\b|[^a-zA-Z0-9$])(?<sig>[a-zA-Z0-9$]{2})\\s*=\\s*function\\(\\s*a\\s*\\)\\s*\\{\\s*a\\s*=\\s*a\\.split\\(\\s*\"\"\\s*\\)")
        //Pattern newPattern3 = Pattern.compile("\\b(?<sig>[a-zA-Z0-9$]{2})\\s*=\\s*function\\(\\s*a\\s*\\)\\s*\\{\\s*a\\s*=\\s*a\\.split\\(\\s*\"\"\\s*\\)");
        val newPattern4 =
            Pattern.compile("(?<sig>[a-zA-Z0-9$]+)\\s*=\\s*function\\(\\s*a\\s*\\)\\s*\\{\\s*a\\s*=\\s*a\\.split\\(\\s*\"\"\\s*\\)")
        // Obsolete patterns
        val obsoletePattern1 =
            Pattern.compile("([\"\\'])signature\\1\\s*,\\s*(?<sig>[a-zA-Z0-9$]+)\\(")
        val obsoletePattern2 = Pattern.compile("\\.sig\\|\\|(?<sig>[a-zA-Z0-9$]+)\\(")
        val obsoletePattern3 =
            Pattern.compile("yt\\.akamaized\\.net/\\)\\s*\\|\\|\\s*.*?\\s*c\\s*&&\\s*d\\.set\\([^,]+\\s*,\\s*(?:encodeURIComponent\\s*\\()?(?<sig>[a-zA-Z0-9$]+)\\(")
        val obsoletePattern4 =
            Pattern.compile("\\bc\\s*&&\\s*d\\.set\\([^,]+\\s*,\\s*(?:encodeURIComponent\\s*\\()?\\s*(?<sig>[a-zA-Z0-9$]+)\\(")
        val obsoletePattern5 =
            Pattern.compile("\\bc\\s*&&\\s*d\\.set\\([^,]+\\s*,\\s*\\([^)]*\\)\\s*\\(\\s*(?<sig>[a-zA-Z0-9$]+)\\(")
        val patterns = Arrays.asList(
            newPattern1,
            newPattern2,
            newPattern3,
            newPattern4,
            obsoletePattern1,
            obsoletePattern2,
            obsoletePattern3,
            obsoletePattern4,
            obsoletePattern5
        )
        return CommonUtils.matchWithPatterns(patterns, playerCode)
            ?: throw ExtractionException("Cannot find required JS function in JS video player code")
    }

    @Throws(SignatureDecryptionException::class)
    private fun preparePlayerUrl(playerUrl: String): String {
        var playerUrl = playerUrl
        if (playerUrl.isEmpty()) {
            throw SignatureDecryptionException("Cannot decrypt signature without player_url!")
        }
        if (playerUrl.startsWith("//")) {
            playerUrl = "https:$playerUrl"
        }
        val pattern = Pattern.compile("https?://")
        val matcher = pattern.matcher(playerUrl)
        playerUrl = if (!matcher.matches()) {
            "https://www.youtube.com$playerUrl"
        } else throw SignatureDecryptionException("Cannot create proper player url with url: $playerUrl")
        return playerUrl
    }
}