package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MuxedStream : Serializable {
    @SerializedName("itag")
    var itag = 0

    @SerializedName("signatureCipher")
    var cipher: Cipher? = null

    @SerializedName("projectionType")
    var projectionType: String? = null

    @SerializedName("bitrate")
    var bitrate = 0

    @SerializedName("mimeType")
    var mimeType: String? = null

    @SerializedName("audioQuality")
    var audioQuality: String? = null

    @SerializedName("approxDurationMs")
    var approxDurationMs: String? = null

    @SerializedName("audioSampleRate")
    var audioSampleRate: String? = null

    @SerializedName("quality")
    var quality: String? = null

    @SerializedName("qualityLabel")
    var qualityLabel: String? = null

    @SerializedName("audioChannels")
    var audioChannels = 0

    @SerializedName("width")
    var width = 0

    @SerializedName("contentLength")
    var contentLength: String? = null

    @SerializedName("lastModified")
    var lastModified: String? = null

    @SerializedName("height")
    var height = 0

    @SerializedName("averageBitrate")
    var averageBitrate = 0

    @SerializedName("url")
    var url: String? = null
        get() {
            if (field == null && cipher != null) {
                field = String.format("%s&%s=%s", cipher!!.url, cipher!!.sp, cipher!!.s)
            }
            return field
        }
        private set

    override fun toString(): String {
        return "NonAdaptiveFormatItem{" +
                "itag = '" + itag + '\'' +
                ",cipher = '" + cipher + '\'' +
                ",projectionType = '" + projectionType + '\'' +
                ",bitrate = '" + bitrate + '\'' +
                ",mimeType = '" + mimeType + '\'' +
                ",audioQuality = '" + audioQuality + '\'' +
                ",approxDurationMs = '" + approxDurationMs + '\'' +
                ",audioSampleRate = '" + audioSampleRate + '\'' +
                ",quality = '" + quality + '\'' +
                ",qualityLabel = '" + qualityLabel + '\'' +
                ",audioChannels = '" + audioChannels + '\'' +
                ",width = '" + width + '\'' +
                ",contentLength = '" + contentLength + '\'' +
                ",lastModified = '" + lastModified + '\'' +
                ",height = '" + height + '\'' +
                ",averageBitrate = '" + averageBitrate + '\'' +
                "}"
    }
}