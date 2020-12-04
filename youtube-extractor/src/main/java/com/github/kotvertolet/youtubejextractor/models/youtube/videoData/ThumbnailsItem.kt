package com.github.kotvertolet.youtubejextractor.models.youtube.videoData

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ThumbnailsItem : Parcelable, Serializable {
    @SerializedName("width")
    var width = 0

    @SerializedName("height")
    var height = 0

    @SerializedName("url")
    var url: String? = null

    constructor() {}
    constructor(width: Int, height: Int, url: String?) {
        this.width = width
        this.height = height
        this.url = url
    }

    protected constructor(`in`: Parcel) {
        width = `in`.readInt()
        height = `in`.readInt()
        url = `in`.readString()
    }

    override fun toString(): String {
        return "ThumbnailsItem{" +
                "width = '" + width + '\'' +
                ",url = '" + url + '\'' +
                ",height = '" + height + '\'' +
                "}"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is ThumbnailsItem) return false
        val that = o
        if (width != that.width) return false
        if (height != that.height) return false
        return if (url != null) url == that.url else that.url == null
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + if (url != null) url.hashCode() else 0
        result = 31 * result + height
        return result
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(width)
        dest.writeInt(height)
        dest.writeString(url)
    }

    companion object {
        val CREATOR: Parcelable.Creator<ThumbnailsItem?> =
            object : Parcelable.Creator<ThumbnailsItem?> {
                override fun createFromParcel(`in`: Parcel): ThumbnailsItem? {
                    return ThumbnailsItem(`in`)
                }

                override fun newArray(size: Int): Array<ThumbnailsItem?> {
                    return arrayOfNulls(size)
                }
            }
    }
}