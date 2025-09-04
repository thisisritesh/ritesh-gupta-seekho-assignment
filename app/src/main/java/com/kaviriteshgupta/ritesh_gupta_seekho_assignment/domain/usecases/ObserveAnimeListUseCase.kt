package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.usecases

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAnimeListUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(): Flow<List<AnimeData>> {
        return animeRepository.observeAnimeList()
    }
}