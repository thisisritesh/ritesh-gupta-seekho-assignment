package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_list

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.usecases.ObserveAnimeListUseCase
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.KEY_PROGRESS_MESSAGE
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.KEY_PROGRESS_STATUS
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.SyncAnimeDataWorker
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.WORK_STATUS_FAILED
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.WORK_STATUS_LOADING
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.WORK_STATUS_SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val observeAnimeListUseCase: ObserveAnimeListUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeListUiState>(AnimeListUiState.Loading)
    val uiState: StateFlow<AnimeListUiState> = _uiState.asStateFlow()


    private val workManager = WorkManager.getInstance(appContext)


    init {
        Log.d("AnimeListViewModel", "Initializing.")
        observeAnimeListFromDatabase()
        observeSyncWorkerStatus()
    }

    private fun observeAnimeListFromDatabase() {
        _uiState.value = AnimeListUiState.Loading
        viewModelScope.launch {
            observeAnimeListUseCase()
                .catch { e ->
                    Log.e("AnimeListViewModel", "Error observing anime list from database.", e)
                    _uiState.value =
                        AnimeListUiState.Error("Failed to load data: ${e.localizedMessage}")
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

    private fun observeSyncWorkerStatus() {
        viewModelScope.launch {
            workManager.getWorkInfosForUniqueWorkFlow(SyncAnimeDataWorker.WORK_NAME)
                .mapNotNull { it.firstOrNull() }
                .catch { e ->
                    Log.e("AnimeListViewModel", "Error observing WorkInfo Flow.", e)
                    _uiState.value =
                        AnimeListUiState.Error("${e.localizedMessage}")
                }
                .collect { workInfo ->
                    val progressData = workInfo.progress
                    val currentProgressMessage = progressData.getString(KEY_PROGRESS_MESSAGE)

                    Log.d(
                        "SyncAnimeDataWorker",
                        "WorkInfo Update: ID=${workInfo.id}, State=${workInfo.state}, ProgressMsg='${currentProgressMessage}', Output=${workInfo.outputData}"
                    )

                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED, WorkInfo.State.BLOCKED, WorkInfo.State.CANCELLED, WorkInfo.State.SUCCEEDED -> {}

                        WorkInfo.State.RUNNING -> {
                            val workStatus = progressData.getString(KEY_PROGRESS_STATUS)
                            when (workStatus) {
                                WORK_STATUS_SUCCESS -> {
                                    Log.d(
                                        "AnimeListViewModel",
                                        "WORK_STATUS_SUCCESS: $currentProgressMessage"
                                    )
                                }

                                WORK_STATUS_LOADING -> {
                                    _uiState.value = AnimeListUiState.Loading
                                    Log.d(
                                        "AnimeListViewModel",
                                        "WORK_STATUS_LOADING: $currentProgressMessage"
                                    )
                                }

                                WORK_STATUS_FAILED -> {
                                    Log.d(
                                        "AnimeListViewModel",
                                        "WORK_STATUS_FAILED: $currentProgressMessage"
                                    )
                                    _uiState.value = AnimeListUiState.Error(currentProgressMessage)
                                }
                            }
                        }

                        WorkInfo.State.FAILED -> {
                            Log.e(
                                "SyncAnimeDataWorker",
                                "AnimeListViewModel => WorkInfo.State.FAILED: $currentProgressMessage"
                            )
                            _uiState.value = AnimeListUiState.Error(currentProgressMessage)
                        }
                    }
                }
        }
    }

}

