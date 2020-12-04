package com.github.kotvertolet.youtubejextractor.utils

import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*

object StringUtils {
    val UTF_8 = StandardCharsets.UTF_8.name()
    @JvmStatic
    fun urlDecode(urlEncodedStr: String?): String {
        return try {
            URLDecoder.decode(urlEncodedStr, UTF_8)
        } catch (e: UnsupportedEncodingException) {
            URLDecoder.decode(urlEncodedStr)
        }
    }

    fun escapeRegExSpecialCharacters(inputString: String): String {
        var inputString = inputString
        val metaCharacters = arrayOf(
            "\\",
            "^",
            "$",
            "{",
            "}",
            "[",
            "]",
            "(",
            ")",
            ".",
            "*",
            "+",
            "?",
            "|",
            "<",
            ">",
            "-",
            "&",
            "%"
        )
        for (metaCharacter in metaCharacters) {
            if (inputString.contains(metaCharacter)) {
                inputString = inputString.replace(metaCharacter, "\\" + metaCharacter)
            }
        }
        return inputString
    }

    fun urlParamsToJson(paramIn: String): String {
        var paramIn = paramIn
        paramIn = paramIn.replace("=".toRegex(), "\":\"")
        paramIn = paramIn.replace("&".toRegex(), "\",\"")
        return "{\"$paramIn\"}"
    }

    @Throws(ExtractionException::class)
    fun splitUrlParams(url: String): Map<String, String?> {
        val queryParams = url.split("&".toRegex()).toTypedArray()
        return splitUrlParams(queryParams)
    }

    @Throws(ExtractionException::class)
    fun splitUrlParams(url: URL): Map<String, String?> {
        val queryParams = url.query.split("&".toRegex()).toTypedArray()
        return splitUrlParams(queryParams)
    }

    @Throws(ExtractionException::class)
    private fun splitUrlParams(queryParamsArr: Array<String>): Map<String, String?> {
        val queryPairs: MutableMap<String, String?> = LinkedHashMap()
        for (queryParam in queryParamsArr) {
            val idx = queryParam.indexOf("=")
            try {
                val key = if (idx > 0) URLDecoder.decode(
                    queryParam.substring(0, idx),
                    UTF_8
                ) else queryParam
                if (!queryPairs.containsKey(key)) {
                    val value = if (idx > 0 && queryParam.length > idx + 1) URLDecoder.decode(
                        queryParam.substring(idx + 1), UTF_8
                    ) else null
                    queryPairs[key] = value
                }
            } catch (e: UnsupportedEncodingException) {
                throw ExtractionException(e)
            }
        }
        return queryPairs
    }
}