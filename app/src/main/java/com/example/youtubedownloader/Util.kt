package com.example.youtubedownloader

var userItag: Int? = null

const val PREFERENCE_NAME = "VIDEO_QUALITY"
const val VIDEO_QUALITY_COUNT = "VIDEO_QUALITY_COUNT"
const val PREF_FORMAT = "PREF_FORMAT"
var currentChar = ""
var mainFormat = 22

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

//val itagList: List<Int> = listOf(278, 133, 18, 135, 22, 248)
//val itagList = listOf(160, 133, 133, 134, 397, 22, 137)
val itagList = listOf(140, 22)

/*
New Itags

22 = 720p = works = video
18 = 360p = works = video
251 = 160k = works = audio
250 = 70k = works = audio
249 = 50k = works = audio
140 = 128k = works = m4a


137 = 1080p = mp4
248 = 1080p = webm
397 = 480p = mp4
134 = 360p = mp4
243 = 360p
396 = 360p
133 = 240p = mp4
242 = 240p = webm
395 = 240p
160 = 144p = m4a
278 = 144p = webm
394 = 144p =
140 = 128k = m4a
249 = 50k = webm
250 = 70k = webm
251 = 160k = webm
18


 */