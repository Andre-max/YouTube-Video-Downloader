package com.github.kotvertolet.youtubejextractor.models

import android.os.Parcelable
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.AdaptiveStream
import java.io.Serializable

abstract class StreamItem : Parcelable, Serializable {
    var extension: String?
    var codec: String?
    var bitrate: Int
    protected var iTag: Int
    var url: String?
    var averageBitrate: Int
    var approxDurationMs: Int

    protected constructor(
        extension: String?,
        codec: String?,
        bitrate: Int,
        iTag: Int,
        url: String?,
        averageBitrate: Int,
        approxDurationMs: Int
    ) {
        this.extension = extension
        this.codec = codec
        this.bitrate = bitrate
        this.iTag = iTag
        this.url = url
        this.averageBitrate = averageBitrate
        this.approxDurationMs = approxDurationMs
    }

    protected constructor(adaptiveStream: AdaptiveStream) {
        val mimeTypeArr = adaptiveStream.mimeType!!.split("[/;]".toRegex()).toTypedArray()
        extension = mimeTypeArr[1]
        codec = mimeTypeArr[2].split("=".toRegex()).toTypedArray()[1].replace("\"".toRegex(), "")
        url = adaptiveStream.url
        iTag = adaptiveStream.itag
        bitrate = adaptiveStream.bitrate
        averageBitrate = adaptiveStream.averageBitrate
        val rawDuration = adaptiveStream.approxDurationMs
        approxDurationMs = if (rawDuration == null) 0 else Integer.valueOf(rawDuration)
    }

    fun getiTag(): Int {
        return iTag
    }

    fun setiTag(iTag: Int) {
        this.iTag = iTag
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as StreamItem
        if (bitrate != that.bitrate) return false
        if (iTag != that.iTag) return false
        if (averageBitrate != that.averageBitrate) return false
        if (if (extension != null) extension != that.extension else that.extension != null) return false
        if (if (codec != null) codec != that.codec else that.codec != null) return false
        if (if (url != null) url != that.url else that.url != null) return false
        return if (approxDurationMs != null) approxDurationMs == that.approxDurationMs else that.approxDurationMs == null
    }

    override fun hashCode(): Int {
        var result = if (extension != null) extension.hashCode() else 0
        result = 31 * result + if (codec != null) codec.hashCode() else 0
        result = 31 * result + bitrate
        result = 31 * result + iTag
        result = 31 * result + if (url != null) url.hashCode() else 0
        result = 31 * result + averageBitrate
        result = 31 * result + if (approxDurationMs != null) approxDurationMs.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "StreamItem{" +
                "extension='" + extension + '\'' +
                ", codec='" + codec + '\'' +
                ", bitrate=" + bitrate +
                ", averageBitrate=" + averageBitrate +
                ", iTag=" + iTag +
                ", url='" + url + '\'' +
                ", approxDurationMs=" + approxDurationMs +
                '}'
    }
}