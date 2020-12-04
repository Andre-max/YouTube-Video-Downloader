package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RawStreamingData : Serializable {
    @SerializedName("formats")
    var muxedStreams: List<MuxedStream>? = null

    @SerializedName("probeUrl")
    var probeUrl: String? = null

    @SerializedName("adaptiveFormats")
    var adaptiveStreams: List<AdaptiveStream>? = null
    var expiresInSeconds: String? = null
    var dashManifestUrl: String? = null
    var hlsManifestUrl: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as RawStreamingData
        if (if (muxedStreams != null) muxedStreams != that.muxedStreams else that.muxedStreams != null) return false
        if (if (probeUrl != null) probeUrl != that.probeUrl else that.probeUrl != null) return false
        if (if (adaptiveStreams != null) adaptiveStreams != that.adaptiveStreams else that.adaptiveStreams != null) return false
        if (if (expiresInSeconds != null) expiresInSeconds != that.expiresInSeconds else that.expiresInSeconds != null) return false
        if (if (dashManifestUrl != null) dashManifestUrl != that.dashManifestUrl else that.dashManifestUrl != null) return false
        return if (hlsManifestUrl != null) hlsManifestUrl == that.hlsManifestUrl else that.hlsManifestUrl == null
    }

    override fun hashCode(): Int {
        var result = if (muxedStreams != null) muxedStreams.hashCode() else 0
        result = 31 * result + if (probeUrl != null) probeUrl.hashCode() else 0
        result = 31 * result + if (adaptiveStreams != null) adaptiveStreams.hashCode() else 0
        result = 31 * result + if (expiresInSeconds != null) expiresInSeconds.hashCode() else 0
        result = 31 * result + if (dashManifestUrl != null) dashManifestUrl.hashCode() else 0
        result = 31 * result + if (hlsManifestUrl != null) hlsManifestUrl.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "RawStreamingData{" +
                "formats=" + muxedStreams +
                ", probeUrl='" + probeUrl + '\'' +
                ", adaptiveFormats=" + adaptiveStreams +
                ", expiresInSeconds='" + expiresInSeconds + '\'' +
                ", dashManifestUrl='" + dashManifestUrl + '\'' +
                ", hlsManifestUrl='" + hlsManifestUrl + '\'' +
                '}'
    }
}