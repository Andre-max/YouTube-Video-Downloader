package com.github.kotvertolet.youtubejextractor.models.youtube.videoData

import android.os.Parcel
import android.os.Parcelable
import com.github.kotvertolet.youtubejextractor.models.AdaptiveAudioStream
import com.github.kotvertolet.youtubejextractor.models.AdaptiveVideoStream
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.AdaptiveStream
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.MuxedStream
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.RawStreamingData
import com.github.kotvertolet.youtubejextractor.utils.CommonUtils.LogE
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class StreamingData : Parcelable, Serializable {
    @SerializedName("expiresInSeconds")
    var expiresInSeconds: String? = null

    @SerializedName("dashManifestUrl")
    var dashManifestUrl: String? = null

    @SerializedName("hlsManifestUrl")
    var hlsManifestUrl: String? = null

    @SerializedName("formats")
    var muxedStreams: List<MuxedStream?>? = null

    @SerializedName("probeUrl")
    var probeUrl: String? = null

    @Expose
    var adaptiveAudioStreams: List<AdaptiveAudioStream?>? = ArrayList()

    @Expose
    var adaptiveVideoStreams: List<AdaptiveVideoStream?>? = ArrayList()

    constructor() {}
    constructor(rawStreamingData: RawStreamingData) {
        expiresInSeconds = rawStreamingData.expiresInSeconds
        dashManifestUrl = rawStreamingData.dashManifestUrl
        hlsManifestUrl = rawStreamingData.hlsManifestUrl
        probeUrl = rawStreamingData.probeUrl
        muxedStreams = rawStreamingData.muxedStreams
        val adaptiveStreams = rawStreamingData.adaptiveStreams
        if (adaptiveStreams != null && adaptiveStreams.size > 0) {
            sortAdaptiveStreamsByType(adaptiveStreams)
        }
    }

    constructor(
        expiresInSeconds: String?,
        dashManifestUrl: String?,
        hlsManifestUrl: String?,
        adaptiveAudioStreams: List<AdaptiveAudioStream?>?,
        adaptiveVideoStreams: List<AdaptiveVideoStream?>?
    ) {
        this.expiresInSeconds = expiresInSeconds
        this.dashManifestUrl = dashManifestUrl
        this.hlsManifestUrl = hlsManifestUrl
        this.adaptiveAudioStreams = adaptiveAudioStreams
        this.adaptiveVideoStreams = adaptiveVideoStreams
    }

    protected constructor(`in`: Parcel) {
        expiresInSeconds = `in`.readString()
        dashManifestUrl = `in`.readString()
        hlsManifestUrl = `in`.readString()
        adaptiveAudioStreams = `in`.createTypedArrayList(AdaptiveAudioStream.CREATOR)
        adaptiveVideoStreams = `in`.createTypedArrayList(AdaptiveVideoStream.CREATOR)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(expiresInSeconds)
        dest.writeString(dashManifestUrl)
        dest.writeString(hlsManifestUrl)
        dest.writeList(adaptiveAudioStreams)
        dest.writeList(adaptiveVideoStreams)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is StreamingData) return false
        val that = o
        if (if (expiresInSeconds != null) expiresInSeconds != that.expiresInSeconds else that.expiresInSeconds != null) return false
        if (if (dashManifestUrl != null) dashManifestUrl != that.dashManifestUrl else that.dashManifestUrl != null) return false
        if (if (hlsManifestUrl != null) hlsManifestUrl != that.hlsManifestUrl else that.hlsManifestUrl != null) return false
        if (if (adaptiveAudioStreams != null) adaptiveAudioStreams != that.adaptiveAudioStreams else that.adaptiveAudioStreams != null) return false
        return if (adaptiveVideoStreams != null) adaptiveVideoStreams == that.adaptiveVideoStreams else that.adaptiveVideoStreams == null
    }

    override fun hashCode(): Int {
        var result = if (expiresInSeconds != null) expiresInSeconds.hashCode() else 0
        result = 31 * result + if (dashManifestUrl != null) dashManifestUrl.hashCode() else 0
        result = 31 * result + if (hlsManifestUrl != null) hlsManifestUrl.hashCode() else 0
        result =
            31 * result + if (adaptiveAudioStreams != null) adaptiveAudioStreams.hashCode() else 0
        result =
            31 * result + if (adaptiveVideoStreams != null) adaptiveVideoStreams.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "RawStreamingData{" +
                "expiresInSeconds='" + expiresInSeconds + '\'' +
                ", dashManifestUrl='" + dashManifestUrl + '\'' +
                ", hlsManifestUrl='" + hlsManifestUrl + '\'' +
                ", audioStreamItems=" + adaptiveAudioStreams +
                ", videoStreamItems=" + adaptiveVideoStreams +
                '}'
    }

    private fun sortAdaptiveStreamsByType(adaptiveStreams: List<AdaptiveStream?>) {
        val adaptiveVideoStreams: MutableList<AdaptiveVideoStream?> = ArrayList()
        val adaptiveAudioStreams: MutableList<AdaptiveAudioStream?> = ArrayList()
        for (adaptiveFormat in adaptiveStreams) {
            val mimeType = adaptiveFormat?.mimeType ?: return
            when {
                mimeType.contains("audio") -> {
                    adaptiveAudioStreams.add(AdaptiveAudioStream(adaptiveFormat))
                }
                mimeType.contains("video") -> {
                    adaptiveVideoStreams.add(AdaptiveVideoStream(adaptiveFormat))
                }
                else -> {
                    LogE(javaClass.simpleName, "Unknown stream type found: $mimeType")
                }
            }
        }
        this.adaptiveAudioStreams = adaptiveAudioStreams
        this.adaptiveVideoStreams = adaptiveVideoStreams
    }

    companion object {
        val CREATOR: Parcelable.Creator<StreamingData?> =
            object : Parcelable.Creator<StreamingData?> {
                override fun createFromParcel(`in`: Parcel): StreamingData? {
                    return StreamingData(`in`)
                }

                override fun newArray(size: Int): Array<StreamingData?> {
                    return arrayOfNulls(size)
                }
            }
    }
}