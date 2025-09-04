package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.di

import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.repository.AnimeRepositoryImpl
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.repository.AnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAnimeRepository(
        animeRepositoryImpl: AnimeRepositoryImpl
    ): AnimeRepository
}