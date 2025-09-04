package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anime_table")
data class AnimeData(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("mal_id")
    val animeId: Int,

    @SerializedName("duration")
    val duration: String?,

    @SerializedName("episodes")
    val episodes: Int?,

    @SerializedName("images")
    val images: PosterImage?,

    @SerializedName("rating")
    val rating: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("trailer")
    val trailer: Trailer?,

    @SerializedName("synopsis")
    val synopsis: String?,

    @SerializedName("genres")
    val genres: List<Genre>?,

    @SerializedName("score")
    val score: Float?,

    @SerializedName("scored_by")
    val scoredBy: Int?,

    var castList: List<Character>?
)