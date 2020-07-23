package com.example.youtubedownloader

var userItag: Int? = null
val itagList: List<Int> = listOf(278, 133, 18, 135, 22, 248)

const val PREFERENCE_NAME = "VIDEO_QUALITY"
const val VIDEO_QUALITY_COUNT = "VIDEO_QUALITY_COUNT"
const val PREF_FORMAT = "PREF_FORMAT"
var currentChar = ""

/*
itag=18, ext='mp4', height=360

itag=278, ext='webm', height=144,

itag=22, ext='mp4', height=720,
itag=247, ext='webm', height=720,

itag=133, ext='mp4', height=240

itag=135, ext='mp4', height=480
itag=244, ext='webm', height=480

itag=248, ext='webm', height=1080
 */
/*
<string-array name="reply_entries">
        <item>144p</item>
        <item>240p</item>
        <item>360p</item>
        <item>480p</item>
        <item>720p</item>
        <item>1080p</item>
    </string-array>
 */