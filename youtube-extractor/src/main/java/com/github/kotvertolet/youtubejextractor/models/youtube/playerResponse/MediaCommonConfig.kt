package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class MediaCommonConfig : Serializable {
    var dynamicReadaheadConfig: DynamicReadaheadConfig? = null
    override fun toString(): String {
        return "MediaCommonConfig{" +
                "dynamicReadaheadConfig = '" + dynamicReadaheadConfig + '\'' +
                "}"
    }
}