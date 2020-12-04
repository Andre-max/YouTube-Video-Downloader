package com.github.kotvertolet.youtubejextractor.models

import android.os.Parcel
import android.os.Parcelable
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.AdaptiveStream

class AdaptiveVideoStream : StreamItem {
    var fps: Int
    var size: String? = null
    var qualityLabel: String?
    var projectionType: String?

    constructor(
        extension: String?,
        codec: String?,
        bitrate: Int,
        iTag: Int,
        url: String?,
        averageBitrate: Int,
        approxDurationMs: Int,
        fps: Int,
        size: String?,
        qualityLabel: String?,
        projectionType: String?
    ) : super(extension, codec, bitrate, iTag, url, averageBitrate, approxDurationMs) {
        this.fps = fps
        this.size = size
        this.qualityLabel = qualityLabel
        this.projectionType = projectionType
    }

    constructor(adaptiveFormat: AdaptiveStream) : super(adaptiveFormat) {
        fps = adaptiveFormat.fps
        qualityLabel = adaptiveFormat.qualityLabel
        projectionType = adaptiveFormat.projectionType
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(extension)
        dest.writeString(codec)
        dest.writeInt(bitrate)
        dest.writeInt(iTag)
        dest.writeString(url)
        dest.writeInt(averageBitrate)
        dest.writeInt(approxDurationMs)
        dest.writeInt(fps)
        dest.writeString(size)
        dest.writeString(qualityLabel)
        dest.writeString(projectionType)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as AdaptiveVideoStream
        if (fps != that.fps) return false
        if (if (size != null) size != that.size else that.size != null) return false
        if (if (qualityLabel != null) qualityLabel != that.qualityLabel else that.qualityLabel != null) return false
        return if (projectionType != null) projectionType == that.projectionType else that.projectionType == null
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + fps
        result = 31 * result + if (size != null) size.hashCode() else 0
        result = 31 * result + if (qualityLabel != null) qualityLabel.hashCode() else 0
        result = 31 * result + if (projectionType != null) projectionType.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "VideoStreamItem{" +
                "fps=" + fps +
                ", size='" + size + '\'' +
                ", qualityLabel='" + qualityLabel + '\'' +
                ", projectionType=" + projectionType +
                ", extension='" + extension + '\'' +
                ", codec='" + codec + '\'' +
                ", bitrate=" + bitrate +
                ", iTag=" + iTag +
                ", url='" + url + '\'' +
                ", averageBitrate=" + averageBitrate +
                ", approxDurationMs=" + approxDurationMs +
                '}'
    }

    companion object {
        val CREATOR: Parcelable.Creator<AdaptiveVideoStream?> =
            object : Parcelable.Creator<AdaptiveVideoStream?> {
                override fun createFromParcel(source: Parcel): AdaptiveVideoStream? {
                    val extension = source.readString()
                    val codec = source.readString()
                    val bitrate = source.readInt()
                    val iTag = source.readInt()
                    val url = source.readString()
                    val averageBitrate = source.readInt()
                    val approxDurationMs = source.readInt()
                    val fps = source.readInt()
                    val size = source.readString()
                    val qualityLabel = source.readString()
                    val projectionType = source.readString()
                    return AdaptiveVideoStream(
                        extension,
                        codec,
                        bitrate,
                        iTag,
                        url,
                        averageBitrate,
                        approxDurationMs,
                        fps,
                        size,
                        qualityLabel,
                        projectionType
                    )
                }

                override fun newArray(size: Int): Array<AdaptiveVideoStream?> {
                    return arrayOfNulls(0)
                }
            }
    }
}