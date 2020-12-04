package com.github.kotvertolet.youtubejextractor.models.youtube.playerConfig

import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.PlayerResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Args : Serializable {
    @SerializedName("show_content_thumbnail")
    var isShowContentThumbnail = false

    @SerializedName("hl")
    var hl: String? = null

    @SerializedName("length_seconds")
    var lengthSeconds: String? = null

    @SerializedName("gapi_hint_params")
    var gapiHintParams: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("ssl")
    var ssl: String? = null

    @SerializedName("fmt_list")
    var fmtList: String? = null

    @SerializedName("cver")
    var cver: String? = null

    @SerializedName("enablecsi")
    var enablecsi: String? = null

    @SerializedName("vss_host")
    var vssHost: String? = null

    @SerializedName("csi_page_type")
    var csiPageType: String? = null

    @SerializedName("fexp")
    var fexp: String? = null

    @SerializedName("innertube_context_client_version")
    var innertubeContextClientVersion: String? = null

    @SerializedName("account_playback_token")
    var accountPlaybackToken: String? = null

    @SerializedName("timestamp")
    var timestamp: String? = null

    @SerializedName("ucid")
    var ucid: String? = null

    @SerializedName("watermark")
    var watermark: String? = null

    @SerializedName("url_encoded_fmt_stream_map")
    var urlEncodedFmtStreamMap: String? = null

    @SerializedName("c")
    var c: String? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("player_response")
    var playerResponse: PlayerResponse? = null

    @SerializedName("enabled_engage_types")
    var enabledEngageTypes: String? = null

    @SerializedName("innertube_api_key")
    var innertubeApiKey: String? = null

    @SerializedName("cr")
    var cr: String? = null

    @SerializedName("host_language")
    var hostLanguage: String? = null

    @SerializedName("innertube_api_version")
    var innertubeApiVersion: String? = null

    @SerializedName("loaderUrl")
    var loaderUrl: String? = null

    @SerializedName("adaptive_fmts")
    var adaptiveFmts: String? = null

    @SerializedName("enablejsapi")
    var enablejsapi: String? = null

    @SerializedName("video_id")
    var videoId: String? = null

    @SerializedName("fflags")
    var fflags: String? = null
    override fun toString(): String {
        return "Args{" +
                "show_content_thumbnail = '" + isShowContentThumbnail + '\'' +
                ",hl = '" + hl + '\'' +
                ",length_seconds = '" + lengthSeconds + '\'' +
                ",gapi_hint_params = '" + gapiHintParams + '\'' +
                ",title = '" + title + '\'' +
                ",ssl = '" + ssl + '\'' +
                ",fmt_list = '" + fmtList + '\'' +
                ",cver = '" + cver + '\'' +
                ",enablecsi = '" + enablecsi + '\'' +
                ",vss_host = '" + vssHost + '\'' +
                ",csi_page_type = '" + csiPageType + '\'' +
                ",fexp = '" + fexp + '\'' +
                ",innertube_context_client_version = '" + innertubeContextClientVersion + '\'' +
                ",account_playback_token = '" + accountPlaybackToken + '\'' +
                ",timestamp = '" + timestamp + '\'' +
                ",ucid = '" + ucid + '\'' +
                ",watermark = '" + watermark + '\'' +
                ",url_encoded_fmt_stream_map = '" + urlEncodedFmtStreamMap + '\'' +
                ",c = '" + c + '\'' +
                ",author = '" + author + '\'' +
                ",player_response = '" + playerResponse + '\'' +
                ",enabled_engage_types = '" + enabledEngageTypes + '\'' +
                ",innertube_api_key = '" + innertubeApiKey + '\'' +
                ",cr = '" + cr + '\'' +
                ",host_language = '" + hostLanguage + '\'' +
                ",innertube_api_version = '" + innertubeApiVersion + '\'' +
                ",loaderUrl = '" + loaderUrl + '\'' +
                ",adaptive_fmts = '" + adaptiveFmts + '\'' +
                ",enablejsapi = '" + enablejsapi + '\'' +
                ",video_id = '" + videoId + '\'' +
                ",fflags = '" + fflags + '\'' +
                "}"
    }
}