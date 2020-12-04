package com.github.kotvertolet.youtubejextractor.utils

import android.util.Log
import com.github.kotvertolet.youtubejextractor.BuildConfig
import com.google.code.regexp.Matcher
import com.google.code.regexp.Pattern

object CommonUtils {
    fun getMatcher(stringPattern: String?, stringMatcher: String?): Matcher {
        val pattern = Pattern.compile(stringPattern)
        return pattern.matcher(stringMatcher)
    }

    @JvmStatic
    fun matchWithPatterns(patterns: List<Pattern>, inputString: String?): String? {
        var outputString: String? = null
        var matcher: Matcher
        val iter = patterns.iterator()
        while (outputString == null && iter.hasNext()) {
            matcher = iter.next().matcher(inputString)
            if (matcher.find()) {
                // Restarting the search
                matcher.find(0)
                outputString = matcher.group(1)
            }
        }
        return outputString
    }

    @JvmStatic
    fun LogI(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message!!)
        }
    }

    @JvmStatic
    fun LogE(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message!!)
        }
    }
}