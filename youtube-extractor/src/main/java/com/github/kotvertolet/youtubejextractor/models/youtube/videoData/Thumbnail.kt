package com.github.kotvertolet.youtubejextractor.models.youtube.videoData

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Thumbnail : Parcelable, Serializable {
    @SerializedName("thumbnails")
    var thumbnails: List<ThumbnailsItem?>? = null

    constructor() {}
    constructor(thumbnails: List<ThumbnailsItem?>?) {
        this.thumbnails = thumbnails
    }

    protected constructor(`in`: Parcel) {
        thumbnails = `in`.createTypedArrayList(ThumbnailsItem.Companion.CREATOR)
    }

    override fun toString(): String {
        return "Thumbnail{" +
                "thumbnails = '" + thumbnails + '\'' +
                "}"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Thumbnail) return false
        val thumbnail = o
        return if (thumbnails != null) thumbnails == thumbnail.thumbnails else thumbnail.thumbnails == null
    }

    override fun hashCode(): Int {
        return if (thumbnails != null) thumbnails.hashCode() else 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(thumbnails)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Thumbnail?> = object : Parcelable.Creator<Thumbnail?> {
            override fun createFromParcel(`in`: Parcel): Thumbnail? {
                return Thumbnail(`in`)
            }

            override fun newArray(size: Int): Array<Thumbnail?> {
                return arrayOfNulls(size)
            }
        }
    }
}