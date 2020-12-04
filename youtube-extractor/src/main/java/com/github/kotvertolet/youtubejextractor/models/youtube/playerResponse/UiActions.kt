package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class UiActions : Serializable {
    var isHideEnclosingContainer = false
    override fun toString(): String {
        return "UiActions{" +
                "hideEnclosingContainer = '" + isHideEnclosingContainer + '\'' +
                "}"
    }
}