package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.usecases.GetAnimeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(private val getAnimeDetailUseCase: GetAnimeDetailUseCase) :
    ViewModel() {

    private val _animeDetail: MutableStateFlow<NetworkResult<AnimeData>> =
        MutableStateFlow(NetworkResult.Loading)
    val animeDetail: Flow<NetworkResult<AnimeData>> = _animeDetail


    fun fetchAnimeDetail(animeId: String) {
        _animeDetail.value = NetworkResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getAnimeDetailUseCase.invoke(animeId).collect {
                _animeDetail.value = it
            }
        }
    }

}