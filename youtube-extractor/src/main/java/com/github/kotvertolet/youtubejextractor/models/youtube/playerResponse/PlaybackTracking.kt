package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlaybackTracking : Serializable {
    var videostatsWatchtimeUrl: VideostatsWatchtimeUrl? = null
    var videostatsDelayplayUrl: VideostatsDelayplayUrl? = null
    var qoeUrl: QoeUrl? = null
    var setAwesomeUrl: SetAwesomeUrl? = null
    var videostatsPlaybackUrl: VideostatsPlaybackUrl? = null
    var ptrackingUrl: PtrackingUrl? = null
    var atrUrl: AtrUrl? = null
    override fun toString(): String {
        return "PlaybackTracking{" +
                "videostatsWatchtimeUrl = '" + videostatsWatchtimeUrl + '\'' +
                ",videostatsDelayplayUrl = '" + videostatsDelayplayUrl + '\'' +
                ",qoeUrl = '" + qoeUrl + '\'' +
                ",setAwesomeUrl = '" + setAwesomeUrl + '\'' +
                ",videostatsPlaybackUrl = '" + videostatsPlaybackUrl + '\'' +
                ",ptrackingUrl = '" + ptrackingUrl + '\'' +
                ",atrUrl = '" + atrUrl + '\'' +
                "}"
    }
}