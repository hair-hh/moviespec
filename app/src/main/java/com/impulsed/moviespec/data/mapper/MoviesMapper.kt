package com.impulsed.moviespec.data.mapper

import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.entity.movies.MoviesEntity
import com.impulsed.moviespec.remote.model.response.allmovies.GetAllMoviesResponse
import com.impulsed.moviespec.remote.model.response.allmovies.toEntity
import com.impulsed.moviespec.remote.model.response.moviedetails.GetMovieDetailsResponse
import com.impulsed.moviespec.remote.model.response.moviedetails.toEntity


fun GetAllMoviesResponse.mapMoviesResponse(): Record<MoviesEntity> {
    return Record(MoviesEntity(this.results.toEntity()), null)
}

fun GetMovieDetailsResponse.mapMovieDetailsResponse(): Record<MovieDetailsEntity> {
    return Record(this.toEntity(), null)
}
