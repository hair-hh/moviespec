package com.impulsed.moviespec.remote.model.response.allmovies

import com.google.gson.annotations.SerializedName

data class GetAllMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Results>
)
