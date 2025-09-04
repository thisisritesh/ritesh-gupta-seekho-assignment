package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("embed_url")
    val embedUrl: String,

    @SerializedName("images")
    val images: Images,

    @SerializedName("url")
    val url: String,

    @SerializedName("youtube_id")
    val youtubeId: String
)