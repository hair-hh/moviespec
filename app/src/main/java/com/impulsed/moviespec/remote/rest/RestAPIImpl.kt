package com.impulsed.moviespec.remote.rest

import com.impulsed.moviespec.remote.model.request.GetAllMoviesRequest
import com.impulsed.moviespec.remote.model.request.GetMovieDetailRequest
import com.impulsed.moviespec.remote.model.response.allmovies.GetAllMoviesResponse
import com.impulsed.moviespec.remote.model.response.moviedetails.GetMovieDetailsResponse
import com.impulsed.moviespec.remote.retrofit.RetrofitAPI
import javax.inject.Inject

class RestAPIImpl @Inject constructor(private val retrofitAPI: RetrofitAPI): RestAPI {
    override suspend fun getAllMovies(getAllMoviesRequest: GetAllMoviesRequest): GetAllMoviesResponse {
        return retrofitAPI.getDiscoverMovies(getAllMoviesRequest.nextPage)
    }

    override suspend fun getMMovieDetails(getMovieDetailRequest: GetMovieDetailRequest): GetMovieDetailsResponse {
        return retrofitAPI.getMovieDetails(getMovieDetailRequest.movieID)
    }
}