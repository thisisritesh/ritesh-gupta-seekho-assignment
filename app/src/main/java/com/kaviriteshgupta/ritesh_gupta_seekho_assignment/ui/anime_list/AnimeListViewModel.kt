package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.usecases.ObserveAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val observeAnimeListUseCase: ObserveAnimeListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeListUiState>(AnimeListUiState.Loading)
    val uiState: StateFlow<AnimeListUiState> = _uiState.asStateFlow()

    init {
        Log.d(
            "AnimeListViewModel",
            "Initializing and starting to observe anime list from database."
        )
        observeAnimeListFromDatabase()
    }

    private fun observeAnimeListFromDatabase() {
        _uiState.value = AnimeListUiState.Loading
        viewModelScope.launch {
            observeAnimeListUseCase()
                .catch { e ->
                    Log.e("AnimeListViewModel", "Error observing anime list from database.", e)
                    _uiState.value =
                        AnimeListUiState.Error("Error loading data from local storage: ${e.localizedMessage}")
                }
                .collect { animeList ->
                    Log.d(
                        "AnimeListViewModel",
                        "Observed anime list from DB. Count: ${animeList.size}"
                    )
                    if (animeList.isEmpty()) {
                        _uiState.value = AnimeListUiState.Loading
                    } else {
                        _uiState.value = AnimeListUiState.Success(animeList)
                    }
                }
        }
    }

}