package com.impulsed.moviespec.domain.entity.movies


data class MovieResultEntity(
    val id: Int,
    val title: String,
    val image: String,
    val rating: Float,
    val releaseDate: String
)
