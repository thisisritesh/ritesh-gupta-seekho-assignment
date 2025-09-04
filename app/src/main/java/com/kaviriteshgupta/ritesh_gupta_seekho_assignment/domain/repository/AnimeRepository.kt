package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAnimeDetail(animeId: String) : Flow<NetworkResult<AnimeData>>

    suspend fun refreshAnimeList() : NetworkResult<Unit>

    suspend fun observeAnimeList(): Flow<List<AnimeData>>

}