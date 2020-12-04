package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AdaptiveStream : Serializable {
    var itag = 0

    @SerializedName(value = "signatureCipher", alternate = ["cipher"])
    var cipher: Cipher? = null
    var indexRange: IndexRange? = null
    var projectionType: String? = null
    var initRange: InitRange? = null
    var bitrate = 0
    var mimeType: String? = null
    var audioQuality: String? = null
    var approxDurationMs: String? = null
    var audioSampleRate: String? = null
    var quality: String? = null
    var audioChannels = 0
    var contentLength: String? = null
    var lastModified: String? = null
    var averageBitrate = 0
    var isHighReplication = false
    var fps = 0
    var qualityLabel: String? = null
    var width = 0
    var height = 0
    var colorInfo: ColorInfo? = null
    var url: String? = null
        get() {
            if (field == null && cipher != null) {
                field = String.format("%s&%s=%s", cipher.getUrl(), cipher.getSp(), cipher!!.s)
            }
            return field
        }
        private set

    override fun toString(): String {
        return "AdaptiveFormatItem{" +
                "itag = '" + itag + '\'' +
                ",cipher = '" + cipher + '\'' +
                ",indexRange = '" + indexRange + '\'' +
                ",projectionType = '" + projectionType + '\'' +
                ",initRange = '" + initRange + '\'' +
                ",bitrate = '" + bitrate + '\'' +
                ",mimeType = '" + mimeType + '\'' +
                ",audioQuality = '" + audioQuality + '\'' +
                ",approxDurationMs = '" + approxDurationMs + '\'' +
                ",audioSampleRate = '" + audioSampleRate + '\'' +
                ",quality = '" + quality + '\'' +
                ",audioChannels = '" + audioChannels + '\'' +
                ",contentLength = '" + contentLength + '\'' +
                ",lastModified = '" + lastModified + '\'' +
                ",averageBitrate = '" + averageBitrate + '\'' +
                ",highReplication = '" + isHighReplication + '\'' +
                ",fps = '" + fps + '\'' +
                ",qualityLabel = '" + qualityLabel + '\'' +
                ",width = '" + width + '\'' +
                ",height = '" + height + '\'' +
                ",colorInfo = '" + colorInfo + '\'' +
                "}"
    }
}