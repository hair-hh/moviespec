package com.impulsed.moviespec.domain.repository

import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.entity.movies.MoviesEntity

interface MovieRepository {
    suspend fun getAllMovies(nextPage: Int): Record<MoviesEntity>
    suspend fun getMovieDetails(movieId: Int): Record<MovieDetailsEntity>
}