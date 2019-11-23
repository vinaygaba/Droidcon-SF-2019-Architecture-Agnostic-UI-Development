package com.droidconsf.architectureagnosticuidevelopment.core.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelResponses(
    @Json(name = "data")
    val data: MarvelData
)

@JsonClass(generateAdapter = true)
data class MarvelData(
    @Json(name = "results")
    val comicsList: List<Comic>
)

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Long,
    val title: String,
    val issueNumber: Int,
    val description: String? = null,
    val thumbnail: ComicThumbnail
)

@JsonClass(generateAdapter = true)
data class ComicThumbnail(
    val extension: String,
    var path: String? = null
) {
    val imageUrl: String
        get() = "$path.$extension"
}