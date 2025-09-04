package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import kotlinx.coroutines.flow.Flow


@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(animeList: List<AnimeData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeData)

    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): Flow<List<AnimeData>>

    @Query("SELECT * FROM anime_table WHERE animeId = :animeId")
    suspend fun getAnimeById(animeId: String): AnimeData?

    @Query("DELETE FROM anime_table")
    suspend fun deleteAllAnime()

}