package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Thumbnail : Serializable {
    var thumbnails: List<ThumbnailsItem>? = null
    override fun toString(): String {
        return "Thumbnail{" +
                "thumbnails = '" + thumbnails + '\'' +
                "}"
    }
}