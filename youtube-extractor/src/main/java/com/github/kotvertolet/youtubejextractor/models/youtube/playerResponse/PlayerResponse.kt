package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import com.github.kotvertolet.youtubejextractor.models.youtube.videoData.VideoDetails
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlayerResponse : Serializable {
    var playerConfig: PlayerConfig? = null
    var trackingParams: String? = null
    var attestation: Attestation? = null
    var videoDetails: VideoDetails? = null

    @SerializedName("streamingData")
    var rawStreamingData: RawStreamingData? = null
    var playabilityStatus: PlayabilityStatus? = null
    var messages: List<MessagesItem>? = null
    var playbackTracking: PlaybackTracking? = null
    var microformat: Microformat? = null
    var storyboards: Storyboards? = null
    override fun toString(): String {
        return "PlayerResponse{" +
                "playerConfig = '" + playerConfig + '\'' +
                ",trackingParams = '" + trackingParams + '\'' +
                ",attestation = '" + attestation + '\'' +
                ",videoDetails = '" + videoDetails + '\'' +
                ",rawStreamingData = '" + rawStreamingData + '\'' +
                ",playabilityStatus = '" + playabilityStatus + '\'' +
                ",messages = '" + messages + '\'' +
                ",playbackTracking = '" + playbackTracking + '\'' +
                ",microformat = '" + microformat + '\'' +
                ",storyboards = '" + storyboards + '\'' +
                "}"
    }
}