package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.di

import android.content.Context
import androidx.room.Room
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.db.AnimeDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {


    @Provides
    @Singleton
    fun provideAnimeDb(@ApplicationContext context: Context): AnimeDb {
        return Room.databaseBuilder(
            context,
            AnimeDb::class.java,
            "Anime_Database_Ritesh"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(db: AnimeDb) = db.animeDao()


}