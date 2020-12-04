package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class ThumbnailsItem : Serializable {
    var width = 0
    var url: String? = null
    var height = 0
    override fun toString(): String {
        return "ThumbnailsItem{" +
                "width = '" + width + '\'' +
                ",url = '" + url + '\'' +
                ",height = '" + height + '\'' +
                "}"
    }
}