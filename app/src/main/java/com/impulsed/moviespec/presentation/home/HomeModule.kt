package com.impulsed.moviespec.presentation.home

import com.impulsed.moviespec.data.MoviesSource
import com.impulsed.moviespec.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object HomeModule {

    @Provides
    fun providesMoviesSource(movieRepository: MovieRepository) =
        MoviesSource(movieRepository = movieRepository)
}