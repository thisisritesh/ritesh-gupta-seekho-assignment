package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.usecases

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(animeId: String): Flow<NetworkResult<AnimeData>> {
        return animeRepository.getAnimeDetail(animeId)
    }
}