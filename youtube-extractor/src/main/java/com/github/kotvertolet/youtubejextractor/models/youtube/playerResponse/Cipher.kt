package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import com.github.kotvertolet.youtubejextractor.utils.StringUtils.urlDecode
import java.io.Serializable

class Cipher(var s: String, var sp: String, var url: String) : Serializable {
    fun getS(): String {
        return urlDecode(s)
    }

    fun setS(s: String) {
        this.s = s
    }
}