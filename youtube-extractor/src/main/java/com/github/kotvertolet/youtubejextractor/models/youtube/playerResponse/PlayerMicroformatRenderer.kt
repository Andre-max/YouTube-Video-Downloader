package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlayerMicroformatRenderer : Serializable {
    var thumbnail: Thumbnail? = null
    var ownerGplusProfileUrl: String? = null
    var externalChannelId: String? = null
    var publishDate: String? = null
    var description: Description? = null
    var lengthSeconds: String? = null
    var title: Title? = null
    var isHasYpcMetadata = false
    var ownerChannelName: String? = null
    var uploadDate: String? = null
    var ownerProfileUrl: String? = null
    var isIsUnlisted = false
        private set
    var embed: Embed? = null
    var viewCount: String? = null
    var category: String? = null
    var isIsFamilySafe = false
        private set
    var availableCountries: List<String>? = null
    fun setIsUnlisted(isUnlisted: Boolean) {
        isIsUnlisted = isUnlisted
    }

    fun setIsFamilySafe(isFamilySafe: Boolean) {
        isIsFamilySafe = isFamilySafe
    }

    override fun toString(): String {
        return "PlayerMicroformatRenderer{" +
                "thumbnail = '" + thumbnail + '\'' +
                ",ownerGplusProfileUrl = '" + ownerGplusProfileUrl + '\'' +
                ",externalChannelId = '" + externalChannelId + '\'' +
                ",publishDate = '" + publishDate + '\'' +
                ",description = '" + description + '\'' +
                ",lengthSeconds = '" + lengthSeconds + '\'' +
                ",title = '" + title + '\'' +
                ",hasYpcMetadata = '" + isHasYpcMetadata + '\'' +
                ",ownerChannelName = '" + ownerChannelName + '\'' +
                ",uploadDate = '" + uploadDate + '\'' +
                ",ownerProfileUrl = '" + ownerProfileUrl + '\'' +
                ",isUnlisted = '" + isIsUnlisted + '\'' +
                ",embed = '" + embed + '\'' +
                ",viewCount = '" + viewCount + '\'' +
                ",category = '" + category + '\'' +
                ",isFamilySafe = '" + isIsFamilySafe + '\'' +
                ",availableCountries = '" + availableCountries + '\'' +
                "}"
    }
}