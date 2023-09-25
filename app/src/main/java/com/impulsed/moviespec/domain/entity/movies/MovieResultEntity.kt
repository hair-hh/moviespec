package com.impulsed.moviespec.domain.entity.movies

import androidx.compose.runtime.Stable
import javax.annotation.concurrent.Immutable

data class MovieResultEntity(
    val id: Int,
    val title: String,
    val image: String,
    val rating: Float
)

@Immutable
@Stable
data class MovieResultsEntity(
    val movieResultsEntity: List<MovieResultEntity>
)
