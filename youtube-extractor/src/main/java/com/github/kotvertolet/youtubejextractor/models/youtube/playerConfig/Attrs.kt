package com.github.kotvertolet.youtubejextractor.models.youtube.playerConfig

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Attrs : Serializable {
    @SerializedName("id")
    var id: String? = null
    override fun toString(): String {
        return "Attrs{" +
                "id = '" + id + '\'' +
                "}"
    }
}