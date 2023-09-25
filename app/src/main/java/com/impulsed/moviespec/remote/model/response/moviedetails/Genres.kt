package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName
import com.impulsed.moviespec.domain.entity.moviedetails.GenresEntity

data class Genres(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

fun Genres.toEntity() = GenresEntity(id, name)

fun List<Genres>.toEntity() = map { it.toEntity() }
