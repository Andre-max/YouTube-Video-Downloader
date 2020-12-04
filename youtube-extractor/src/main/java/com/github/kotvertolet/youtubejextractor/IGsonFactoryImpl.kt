package com.github.kotvertolet.youtubejextractor

import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.Cipher
import com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse.PlayerResponse
import com.github.kotvertolet.youtubejextractor.utils.StringUtils.urlDecode
import com.google.gson.*
import java.lang.reflect.Type

internal class IGsonFactoryImpl : IGsonFactory {
    override fun initGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        val cipherDeserializer =
            JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
//            JsonObject jsonObject = json.getAsJsonObject();
//            String s = jsonObject.get("s").getAsString();
//            String sp = jsonObject.get("sp").getAsString();
//            String url = urlDecode(jsonObject.get("url").getAsString());
                val arr = json.asString.split("&".toRegex()).toTypedArray()
                Cipher(
                    arr[0].replace("s=", ""), arr[1].replace("sp=", ""), urlDecode(
                        arr[2].replace("url=", "")
                    )
                )
            }
        val playerResponseJsonDeserializer =
            JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                val tempGson = GsonBuilder().registerTypeAdapter(
                    Cipher::class.java, cipherDeserializer
                ).create()
                val jsonRaw = json.asString
                tempGson.fromJson(jsonRaw, PlayerResponse::class.java)
            }
        gsonBuilder.registerTypeAdapter(PlayerResponse::class.java, playerResponseJsonDeserializer)
        return gsonBuilder.create()
    }
}