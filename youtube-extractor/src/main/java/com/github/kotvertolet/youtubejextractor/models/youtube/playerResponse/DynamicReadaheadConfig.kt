package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class DynamicReadaheadConfig : Serializable {
    var readAheadGrowthRateMs = 0
    var maxReadAheadMediaTimeMs = 0
    var minReadAheadMediaTimeMs = 0
    override fun toString(): String {
        return "DynamicReadaheadConfig{" +
                "readAheadGrowthRateMs = '" + readAheadGrowthRateMs + '\'' +
                ",maxReadAheadMediaTimeMs = '" + maxReadAheadMediaTimeMs + '\'' +
                ",minReadAheadMediaTimeMs = '" + minReadAheadMediaTimeMs + '\'' +
                "}"
    }
}