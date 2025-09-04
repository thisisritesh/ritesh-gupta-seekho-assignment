package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models

import com.google.gson.annotations.SerializedName

data class AnimeDetailResponse(
    @SerializedName("data")
    val animeData: AnimeData
)