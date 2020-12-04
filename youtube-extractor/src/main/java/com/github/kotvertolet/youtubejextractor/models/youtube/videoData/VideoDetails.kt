package com.github.kotvertolet.youtubejextractor.models.youtube.videoData

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class VideoDetails : Parcelable, Serializable {
    @SerializedName("isOwnerViewing")
    var isIsOwnerViewing = false
        private set

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null

    @SerializedName("isLiveContent")
    var isLiveContent = false

    @SerializedName("keywords")
    var keywords: List<String?>? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("lengthSeconds")
    var lengthSeconds: String? = null

    @SerializedName("videoId")
    var videoId: String? = null

    @SerializedName("shortDescription")
    var shortDescription: String? = null

    @SerializedName("isPrivate")
    var isIsPrivate = false
        private set

    @SerializedName("title")
    var title: String? = null

    @SerializedName("isCrawlable")
    var isIsCrawlable = false
        private set

    @SerializedName("averageRating")
    var averageRating = 0.0

    @SerializedName("isUnpluggedCorpus")
    var isIsUnpluggedCorpus = false
        private set

    @SerializedName("allowRatings")
    var isAllowRatings = false

    @SerializedName("viewCount")
    var viewCount: String? = null

    @SerializedName("channelId")
    var channelId: String? = null

    constructor() {}
    constructor(
        isOwnerViewing: Boolean, thumbnail: Thumbnail?, isLiveContent: Boolean,
        keywords: List<String?>?, author: String?, lengthSeconds: String?, videoId: String?,
        shortDescription: String?, isPrivate: Boolean, title: String?, isCrawlable: Boolean,
        averageRating: Double, isUnpluggedCorpus: Boolean, allowRatings: Boolean,
        viewCount: String?, channelId: String?
    ) {
        isIsOwnerViewing = isOwnerViewing
        this.thumbnail = thumbnail
        this.isLiveContent = isLiveContent
        this.keywords = keywords
        this.author = author
        this.lengthSeconds = lengthSeconds
        this.videoId = videoId
        this.shortDescription = shortDescription
        isIsPrivate = isPrivate
        this.title = title
        isIsCrawlable = isCrawlable
        this.averageRating = averageRating
        isIsUnpluggedCorpus = isUnpluggedCorpus
        isAllowRatings = allowRatings
        this.viewCount = viewCount
        this.channelId = channelId
    }

    protected constructor(`in`: Parcel) {
        isIsOwnerViewing = `in`.readInt() != 0
        thumbnail = `in`.readParcelable(Thumbnail::class.java.classLoader)
        isLiveContent = `in`.readInt() != 0
        keywords = `in`.createStringArrayList()
        author = `in`.readString()
        lengthSeconds = `in`.readString()
        videoId = `in`.readString()
        shortDescription = `in`.readString()
        isIsPrivate = `in`.readInt() != 0
        title = `in`.readString()
        isIsCrawlable = `in`.readInt() != 0
        averageRating = `in`.readDouble()
        isIsUnpluggedCorpus = `in`.readInt() != 0
        isAllowRatings = `in`.readInt() != 0
        viewCount = `in`.readString()
        channelId = `in`.readString()
    }

    fun setIsOwnerViewing(isOwnerViewing: Boolean) {
        isIsOwnerViewing = isOwnerViewing
    }

    fun setIsPrivate(isPrivate: Boolean) {
        isIsPrivate = isPrivate
    }

    fun setIsCrawlable(isCrawlable: Boolean) {
        isIsCrawlable = isCrawlable
    }

    fun setIsUnpluggedCorpus(isUnpluggedCorpus: Boolean) {
        isIsUnpluggedCorpus = isUnpluggedCorpus
    }

    override fun toString(): String {
        return "VideoDetails{" +
                "isOwnerViewing = '" + isIsOwnerViewing + '\'' +
                ",thumbnail = '" + thumbnail + '\'' +
                ",isLiveContent = '" + isLiveContent + '\'' +
                ",keywords = '" + keywords + '\'' +
                ",author = '" + author + '\'' +
                ",lengthSeconds = '" + lengthSeconds + '\'' +
                ",videoId = '" + videoId + '\'' +
                ",shortDescription = '" + shortDescription + '\'' +
                ",isPrivate = '" + isIsPrivate + '\'' +
                ",title = '" + title + '\'' +
                ",isCrawlable = '" + isIsCrawlable + '\'' +
                ",averageRating = '" + averageRating + '\'' +
                ",isUnpluggedCorpus = '" + isIsUnpluggedCorpus + '\'' +
                ",allowRatings = '" + isAllowRatings + '\'' +
                ",viewCount = '" + viewCount + '\'' +
                ",channelId = '" + channelId + '\'' +
                "}"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is VideoDetails) return false
        val that = o
        if (isIsOwnerViewing != that.isIsOwnerViewing) return false
        if (isLiveContent != that.isLiveContent) return false
        if (isIsPrivate != that.isIsPrivate) return false
        if (isIsCrawlable != that.isIsCrawlable) return false
        if (java.lang.Double.compare(that.averageRating, averageRating) != 0) return false
        if (isIsUnpluggedCorpus != that.isIsUnpluggedCorpus) return false
        if (isAllowRatings != that.isAllowRatings) return false
        if (if (thumbnail != null) thumbnail != that.thumbnail else that.thumbnail != null) return false
        if (if (keywords != null) keywords != that.keywords else that.keywords != null) return false
        if (if (author != null) author != that.author else that.author != null) return false
        if (if (lengthSeconds != null) lengthSeconds != that.lengthSeconds else that.lengthSeconds != null) return false
        if (if (videoId != null) videoId != that.videoId else that.videoId != null) return false
        if (if (shortDescription != null) shortDescription != that.shortDescription else that.shortDescription != null) return false
        if (if (title != null) title != that.title else that.title != null) return false
        if (if (viewCount != null) viewCount != that.viewCount else that.viewCount != null) return false
        return if (channelId != null) channelId == that.channelId else that.channelId == null
    }

    override fun hashCode(): Int {
        var result: Int
        val temp: Long
        result = if (isIsOwnerViewing) 1 else 0
        result = 31 * result + if (thumbnail != null) thumbnail.hashCode() else 0
        result = 31 * result + if (isLiveContent) 1 else 0
        result = 31 * result + if (keywords != null) keywords.hashCode() else 0
        result = 31 * result + if (author != null) author.hashCode() else 0
        result = 31 * result + if (lengthSeconds != null) lengthSeconds.hashCode() else 0
        result = 31 * result + if (videoId != null) videoId.hashCode() else 0
        result = 31 * result + if (shortDescription != null) shortDescription.hashCode() else 0
        result = 31 * result + if (isIsPrivate) 1 else 0
        result = 31 * result + if (title != null) title.hashCode() else 0
        result = 31 * result + if (isIsCrawlable) 1 else 0
        temp = java.lang.Double.doubleToLongBits(averageRating)
        result = 31 * result + (temp xor (temp ushr 32)).toInt()
        result = 31 * result + if (isIsUnpluggedCorpus) 1 else 0
        result = 31 * result + if (isAllowRatings) 1 else 0
        result = 31 * result + if (viewCount != null) viewCount.hashCode() else 0
        result = 31 * result + if (channelId != null) channelId.hashCode() else 0
        return result
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (isIsOwnerViewing) 1 else 0)
        dest.writeParcelable(thumbnail, flags)
        dest.writeInt(if (isLiveContent) 1 else 0)
        dest.writeList(keywords)
        dest.writeString(author)
        dest.writeString(lengthSeconds)
        dest.writeString(videoId)
        dest.writeString(shortDescription)
        dest.writeInt(if (isIsPrivate) 1 else 0)
        dest.writeString(title)
        dest.writeInt(if (isIsCrawlable) 1 else 0)
        dest.writeDouble(averageRating)
        dest.writeInt(if (isIsUnpluggedCorpus) 1 else 0)
        dest.writeInt(if (isAllowRatings) 1 else 0)
        dest.writeString(viewCount)
        dest.writeString(channelId)
    }

    companion object {
        val CREATOR: Parcelable.Creator<VideoDetails?> = object : Parcelable.Creator<VideoDetails?> {
            override fun createFromParcel(`in`: Parcel): VideoDetails? {
                return VideoDetails(`in`)
            }

            override fun newArray(size: Int): Array<VideoDetails?> {
                return arrayOfNulls(size)
            }
        }
    }
}