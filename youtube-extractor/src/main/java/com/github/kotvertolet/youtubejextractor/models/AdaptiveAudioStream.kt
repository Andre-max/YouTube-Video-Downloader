package com.github.kotvertolet.youtubejextractor.models

import android.os.Parcel
import android.os.Parcelable
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.AdaptiveStream

class AdaptiveAudioStream : StreamItem {
    var audioChannels: Int
    var audioSampleRate: Int

    constructor(
        extension: String?, codec: String?, bitrate: Int, iTag: Int, url: String?,
        audioChannels: Int, audioSampleRate: Int, averageBitrate: Int, approxDurationMs: Int
    ) : super(extension, codec, bitrate, iTag, url, averageBitrate, approxDurationMs) {
        this.audioChannels = audioChannels
        this.audioSampleRate = audioSampleRate
    }

    constructor(adaptiveStream: AdaptiveStream) : super(adaptiveStream) {
        audioChannels = adaptiveStream.audioChannels
        audioSampleRate = Integer.valueOf(adaptiveStream.audioSampleRate)
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
        dest.writeInt(audioChannels)
        dest.writeInt(audioSampleRate)
        dest.writeInt(averageBitrate)
        dest.writeInt(approxDurationMs)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as AdaptiveAudioStream
        return if (audioChannels != that.audioChannels) false else audioSampleRate == that.audioSampleRate
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + audioChannels
        result = 31 * result + audioSampleRate
        return result
    }

    companion object {
        val CREATOR: Parcelable.Creator<AdaptiveAudioStream?> =
            object : Parcelable.Creator<AdaptiveAudioStream?> {
                override fun createFromParcel(source: Parcel): AdaptiveAudioStream? {
                    val extension = source.readString()
                    val codec = source.readString()
                    val bitrate = source.readInt()
                    val iTag = source.readInt()
                    val url = source.readString()
                    val audioChannels = source.readInt()
                    val audioSampleRate = source.readInt()
                    val averageBitrate = source.readInt()
                    val approxDurationMs = source.readInt()
                    return AdaptiveAudioStream(
                        extension, codec, bitrate, iTag, url, audioChannels,
                        audioSampleRate, averageBitrate, approxDurationMs
                    )
                }

                override fun newArray(size: Int): Array<AdaptiveAudioStream?> {
                    return arrayOfNulls(size)
                }
            }
    }
}