package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.repository

import android.util.Log
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.db.AnimeDao
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.AnimeApi
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkHelper
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeDetailResponse
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.CastResponse
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.Character
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
    private val animeDao: AnimeDao,
    private val networkHelper: NetworkHelper
) : AnimeRepository {

    companion object {
        private const val TAG = "AnimeRepositoryImpl"
    }

    override suspend fun getAnimeDetail(animeId: String): Flow<NetworkResult<AnimeData>> = flow {
        emit(NetworkResult.Loading)

        if (networkHelper.isNetworkConnected()) {
            try {
                coroutineScope {
                    val detailDeferred = async {
                        animeApi.getAnimeDetail(animeId)
                    }

                    val castDeferred = async {
                        animeApi.getAnimeCast(animeId)
                    }

                    val detailResponse = detailDeferred.await()
                    val castResponse = castDeferred.await()

                    val animeData = mapResponsesToAnimeData(detailResponse, castResponse)

                    animeDao.insertAnime(animeData)
                    emit(NetworkResult.Success(animeData))
                }
            } catch (e: Exception) {
                val cachedData = animeDao.getAnimeById(animeId)
                if (cachedData != null) {
                    emit(NetworkResult.Success(cachedData))
                } else {
                    emit(NetworkResult.Error(e))
                }
            }
        } else {
            val cachedData = animeDao.getAnimeById(animeId)
            if (cachedData != null) {
                emit(NetworkResult.Success(cachedData))
            } else {
                emit(NetworkResult.Error(Throwable("No internet connection and no local data available")))
            }
        }

    }

    override suspend fun refreshAnimeList(): NetworkResult<Unit> {
        Log.d(TAG, "Attempting to refresh anime list from network.")

        if (!networkHelper.isNetworkConnected()) {
            Log.w(TAG, "Refresh failed: No network connection.")
            return NetworkResult.Error(Throwable("No internet connection to refresh the list."))
        }

        return try {
            Log.d(TAG, "Fetching anime list from API...")
            val networkResponse = animeApi.getAnimeList()
            val animeDataList = networkResponse.animeData

            Log.d(TAG, "Inserting ${animeDataList.size} anime into DAO.")
            animeDao.deleteAllAnime()
            animeDao.insertAllAnime(animeDataList)

            Log.i(TAG, "Anime list refreshed and saved to DB successfully.")
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing anime list from network: ${e.message}", e)
            NetworkResult.Error(Throwable("Failed to refresh anime list: ${e.message ?: "Unknown error"}"))
        }
    }

    override suspend fun observeAnimeList(): Flow<List<AnimeData>> {
        Log.d(TAG, "Observing anime list from DAO")
        return animeDao.getAllAnime().distinctUntilChanged()
    }

    private fun mapResponsesToAnimeData(
        detailResponse: AnimeDetailResponse,
        castResponse: CastResponse
    ): AnimeData {
        val animeData = detailResponse.animeData

        val list: MutableList<Character> = mutableListOf()

        for (character in castResponse.castData) {
            if (character.role == "Main") {
                list.add(character.character)
            }
        }
        animeData.castList = list
        return animeData
    }

}