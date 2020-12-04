package com.github.kotvertolet.youtubejextractor.models.subtitles

class Subtitle(var start: String?, var duration: String?, var text: String?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val subtitle = other as Subtitle
        if (if (start != null) start != subtitle.start else subtitle.start != null) return false
        if (if (duration != null) duration != subtitle.duration else subtitle.duration != null) return false
        return if (text != null) text == subtitle.text else subtitle.text == null
    }

    override fun hashCode(): Int {
        var result = if (start != null) start.hashCode() else 0
        result = 31 * result + if (duration != null) duration.hashCode() else 0
        result = 31 * result + if (text != null) text.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "Subtitle{" +
                "start='" + start + '\'' +
                ", duration='" + duration + '\'' +
                ", text='" + text + '\'' +
                '}'
    }
}