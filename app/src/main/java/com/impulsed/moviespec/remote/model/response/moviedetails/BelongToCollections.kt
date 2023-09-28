package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName

data class BelongToCollections(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
)
