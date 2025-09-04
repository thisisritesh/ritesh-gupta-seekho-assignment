package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeDetailResponse
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeListResponse
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.CastResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("top/anime")
    suspend fun getAnimeList(): AnimeListResponse

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetail(@Path("anime_id") animeId: String): AnimeDetailResponse

    @GET("anime/{anime_id}/characters")
    suspend fun getAnimeCast(@Path("anime_id") animeId: String): CastResponse

}