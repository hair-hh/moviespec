package com.impulsed.moviespec.domain.interactor

import com.impulsed.moviespec.domain.BaseUseCase
import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository):
    BaseUseCase<MovieDetailsUseCase.RequestValue, Record<MovieDetailsEntity>>(){

    override suspend fun run(request: RequestValue): Record<MovieDetailsEntity> {
        return movieRepository.getMovieDetails(request.movieId)
    }

    data class RequestValue(
        val movieId: Int
    )
}