package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData

@Database(entities = [AnimeData::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AnimeDb : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}