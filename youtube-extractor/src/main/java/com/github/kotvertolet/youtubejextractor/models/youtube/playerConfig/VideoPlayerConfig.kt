package com.github.kotvertolet.youtubejextractor.models.youtube.playerConfig

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class VideoPlayerConfig : Serializable {
    @SerializedName("args")
    var args: Args? = null

    @SerializedName("sts")
    var sts = 0

    @SerializedName("assets")
    var assets: Assets? = null

    @SerializedName("attrs")
    var attrs: Attrs? = null
    override fun toString(): String {
        return "VideoPlayerConfig{" +
                "args = '" + args + '\'' +
                ",sts = '" + sts + '\'' +
                ",assets = '" + assets + '\'' +
                ",attrs = '" + attrs + '\'' +
                "}"
    }
}