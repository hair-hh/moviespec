package com.impulsed.moviespec.domain.entity.moviedetails

import androidx.compose.runtime.Stable
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
data class MovieDetailsEntity(
    val id: Int,
    val budget: Int,
    val homepage: String,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overView: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val voteAverage: Float,
    val voteCount: Int,
    val title: String,
    val genres: List<GenresEntity>,
    val productionCompanies: List<ProductionCompaniesEntity>,
    val productionCountries: List<ProductionCountriesEntity>,
    val spokenLanguages: List<SpokenLanguagesEntity>
) {
}