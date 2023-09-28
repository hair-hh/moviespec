package com.impulsed.moviespec.data.repository

import com.impulsed.moviespec.data.DataSource
import com.impulsed.moviespec.data.mapper.mapErrorRecord
import com.impulsed.moviespec.data.mapper.mapMovieDetailsResponse
import com.impulsed.moviespec.data.mapper.mapMoviesResponse
import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.entity.movies.MoviesEntity
import com.impulsed.moviespec.domain.repository.MovieRepository
import com.impulsed.moviespec.remote.RemoteException
import com.impulsed.moviespec.remote.model.request.GetAllMoviesRequest
import com.impulsed.moviespec.remote.model.request.GetMovieDetailRequest
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val dataSource: DataSource): MovieRepository {


    override suspend fun getAllMovies(nextPage: Int): Record<MoviesEntity> {
        return try {
            dataSource.api().restAPI().getAllMovies(GetAllMoviesRequest(nextPage)).run {
                this.mapMoviesResponse()
            }
        } catch (e: RemoteException) {
            e.mapErrorRecord()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Record<MovieDetailsEntity> {
        return try {
            dataSource.api().restAPI().getMMovieDetails(GetMovieDetailRequest(movieId)).run {
                this.mapMovieDetailsResponse()
            }
        } catch (e: RemoteException) {
            e.mapErrorRecord()
        }
    }
}