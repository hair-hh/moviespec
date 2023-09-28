package com.impulsed.moviespec.remote.rest

import com.impulsed.moviespec.remote.model.request.GetAllMoviesRequest
import com.impulsed.moviespec.remote.model.request.GetMovieDetailRequest
import com.impulsed.moviespec.remote.model.response.allmovies.GetAllMoviesResponse
import com.impulsed.moviespec.remote.model.response.moviedetails.GetMovieDetailsResponse

interface RestAPI {
    suspend fun getAllMovies(getAllMoviesRequest: GetAllMoviesRequest): GetAllMoviesResponse

    suspend fun getMMovieDetails(getMovieDetailRequest: GetMovieDetailRequest): GetMovieDetailsResponse
}