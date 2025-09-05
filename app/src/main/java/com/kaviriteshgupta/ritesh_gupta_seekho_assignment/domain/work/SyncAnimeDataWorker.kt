package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


const val KEY_PROGRESS_STATUS = "PROGRESS_STATUS"
const val KEY_PROGRESS_MESSAGE = "PROGRESS_MESSAGE"
const val WORK_STATUS_SUCCESS = "SUCCESS"
const val WORK_STATUS_FAILED = "FAILED"
const val WORK_STATUS_LOADING = "LOADING"

@HiltWorker
class SyncAnimeDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val animeRepository: AnimeRepository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "SyncAnimeDataWorker"
        const val TAG = "SyncAnimeDataWorker"
    }

    override suspend fun doWork(): Result {
        Log.e(TAG, "Background sync started for anime list. = ${Thread.currentThread().name}")
        setProgress(
            workDataOf(
                KEY_PROGRESS_STATUS to WORK_STATUS_LOADING,
                KEY_PROGRESS_MESSAGE to "Attempting to connect.."
            )
        )
        delay()

        return when (val refreshResult = animeRepository.refreshAnimeList()) {
            is NetworkResult.Success -> {
                Log.d(TAG, "Background sync successful: Anime list refreshed in DB")
                setProgress(
                    workDataOf(
                        KEY_PROGRESS_STATUS to WORK_STATUS_SUCCESS,
                        KEY_PROGRESS_MESSAGE to "Sync successful!"
                    )
                )
                delay()
                Result.success()
            }

            is NetworkResult.Error -> {
                val errorMessage = "Error: ${refreshResult.exception.message ?: "Unknown error"}"
                Log.e(TAG, "Background sync failed: $errorMessage.")
                setProgress(
                    workDataOf(
                        KEY_PROGRESS_STATUS to WORK_STATUS_FAILED,
                        KEY_PROGRESS_MESSAGE to errorMessage
                    )
                )
                delay()
                Result.failure(workDataOf(KEY_PROGRESS_MESSAGE to errorMessage))
            }

            is NetworkResult.Loading -> {
                Log.w(TAG, "Background sync in unexpected Loading state. Retrying")
                setProgress(
                    workDataOf(
                        KEY_PROGRESS_STATUS to WORK_STATUS_LOADING,
                        KEY_PROGRESS_MESSAGE to "Unexpected state, will retry..."
                    )
                )
                delay()
                Result.retry()
            }
        }
    }

    private fun delay() {
        runBlocking {
            delay(1000)
        }
    }
}