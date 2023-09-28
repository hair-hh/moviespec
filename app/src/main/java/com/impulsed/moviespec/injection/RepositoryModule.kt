package com.impulsed.moviespec.injection

import com.impulsed.moviespec.data.repository.MoviesRepositoryImpl
import com.impulsed.moviespec.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @[Binds Singleton]
    fun providesMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MovieRepository
}