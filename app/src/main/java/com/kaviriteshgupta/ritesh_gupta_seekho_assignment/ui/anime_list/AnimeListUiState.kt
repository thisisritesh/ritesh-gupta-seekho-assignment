package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_list

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData

sealed interface AnimeListUiState {
    data object Loading : AnimeListUiState
    data class Success(val anime: List<AnimeData>) : AnimeListUiState
    data class Error(val message: String) : AnimeListUiState
}