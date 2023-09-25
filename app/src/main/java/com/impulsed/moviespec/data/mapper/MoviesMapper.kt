package com.impulsed.moviespec.data.mapper

import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.entity.movies.MoviesEntity
import com.impulsed.moviespec.remote.model.response.allmovies.GetAllMoviesResponse
import com.impulsed.moviespec.remote.model.response.allmovies.toEntity
import com.impulsed.moviespec.remote.model.response.moviedetails.GetMovieDetailsResponse
import com.impulsed.moviespec.remote.model.response.moviedetails.toEntity

class MoviesMapper {
    fun mapMoviesResponse(allMoviesResponse: GetAllMoviesResponse): Record<MoviesEntity> {
        return Record(MoviesEntity(allMoviesResponse.results.toEntity()), null)
    }

    fun mapMovieDetailsResponse(movieDetailsResponse: GetMovieDetailsResponse): Record<MovieDetailsEntity> {
        return Record(movieDetailsResponse.toEntity(), null)
    }
}