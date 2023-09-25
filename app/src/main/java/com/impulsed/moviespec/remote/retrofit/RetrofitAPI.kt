package com.impulsed.moviespec.remote.retrofit

import com.impulsed.moviespec.remote.model.response.allmovies.GetAllMoviesResponse
import com.impulsed.moviespec.remote.model.response.moviedetails.GetMovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/3/discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en_US",
        @Query("sort_by") sortBy: String = "popularity.desc"
    ) : GetAllMoviesResponse

    @GET("/3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): GetMovieDetailsResponse
}