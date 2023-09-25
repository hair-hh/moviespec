package com.impulsed.moviespec.data.repository

import com.impulsed.moviespec.data.DataSource
import com.impulsed.moviespec.data.mapper.ErrorMapper
import com.impulsed.moviespec.data.mapper.MoviesMapper
import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.domain.entity.movies.MoviesEntity
import com.impulsed.moviespec.domain.repository.MovieRepository
import com.impulsed.moviespec.remote.RemoteException
import com.impulsed.moviespec.remote.model.request.GetAllMoviesRequest
import com.impulsed.moviespec.remote.model.request.GetMovieDetailRequest
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val dataSource: DataSource): MovieRepository {

    private val moviesMapper = MoviesMapper()
    private val errorMapper = ErrorMapper()
    override suspend fun getAllMovies(nextPage: Int): Record<MoviesEntity> {
        return try {
            dataSource.api().restAPI().getAllMovies(GetAllMoviesRequest(nextPage)).run {
                moviesMapper.mapMoviesResponse(this)
            }
        } catch (e: RemoteException) {
            errorMapper.mapErrorRecord(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Record<MovieDetailsEntity> {
        return try {
            dataSource.api().restAPI().getMMovieDetails(GetMovieDetailRequest(movieId)).run {
                moviesMapper.mapMovieDetailsResponse(this)
            }
        } catch (e: RemoteException) {
            errorMapper.mapErrorRecord(e)
        }
    }
}