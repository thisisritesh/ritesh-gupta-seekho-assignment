package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

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
        Log.d(TAG, "Background sync started for anime list. = ${Thread.currentThread().name}")

        return when (val refreshResult = animeRepository.refreshAnimeList()) {
            is NetworkResult.Success -> {
                Log.d(TAG, "Background sync successful: Anime list refreshed in DB")
                Result.success()
            }
            is NetworkResult.Error -> {
                Log.e(TAG, "Background sync failed: ${refreshResult.exception.message}. Retrying")
                Result.retry()
            }
            is NetworkResult.Loading -> {
                Log.w(TAG, "Background sync in unexpected Loading state. Retrying")
                Result.retry()
            }
        }
    }
}