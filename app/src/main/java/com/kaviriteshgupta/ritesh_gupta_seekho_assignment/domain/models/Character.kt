package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("images")
    val images: PosterImage,
    @SerializedName("name")
    val name: String,
    @SerializedName("mal_id")
    val malId: Int
)