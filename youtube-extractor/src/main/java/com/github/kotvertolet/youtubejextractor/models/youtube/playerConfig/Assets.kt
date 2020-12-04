package com.github.kotvertolet.youtubejextractor.models.youtube.playerConfig

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Assets : Serializable {
    @SerializedName("css")
    var css: String? = null

    @SerializedName("js")
    var js: String? = null
    override fun toString(): String {
        return "Assets{" +
                "css = '" + css + '\'' +
                ",js = '" + js + '\'' +
                "}"
    }
}