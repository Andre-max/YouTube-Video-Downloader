package com.github.kotvertolet.youtubejextractor.models.youtube.videoData

import android.os.Parcel
import android.os.Parcelable
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.RawStreamingData
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class YoutubeVideoData : Parcelable, Serializable {
    @SerializedName("videoDetails")
    var videoDetails: VideoDetails? = null

    @SerializedName("streamingData")
    var streamingData: StreamingData? = null

    constructor() {}
    constructor(videoDetails: VideoDetails?, streamingData: RawStreamingData) {
        this.videoDetails = videoDetails
        this.streamingData = StreamingData(streamingData)
    }

    protected constructor(`in`: Parcel) {
        videoDetails = `in`.readParcelable(VideoDetails::class.java.classLoader)
        streamingData = `in`.readParcelable(StreamingData::class.java.classLoader)
    }

    override fun toString(): String {
        return "YoutubeVideoData{" +
                "videoDetails=" + videoDetails +
                ", streamingData=" + streamingData +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is YoutubeVideoData) return false
        val that = o
        return if (videoDetails != that.videoDetails) false else streamingData == that.streamingData
    }

    override fun hashCode(): Int {
        var result = videoDetails.hashCode()
        result = 31 * result + streamingData.hashCode()
        return result
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(videoDetails, flags)
        dest.writeParcelable(streamingData, flags)
    }

    companion object {
        val CREATOR: Parcelable.Creator<YoutubeVideoData?> =
            object : Parcelable.Creator<YoutubeVideoData?> {
                override fun createFromParcel(`in`: Parcel): YoutubeVideoData? {
                    return YoutubeVideoData(`in`)
                }

                override fun newArray(size: Int): Array<YoutubeVideoData?> {
                    return arrayOfNulls(size)
                }
            }
    }
}