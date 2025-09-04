package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models

import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("character")
    val character: Character,
    @SerializedName("role")
    val role: String,
)