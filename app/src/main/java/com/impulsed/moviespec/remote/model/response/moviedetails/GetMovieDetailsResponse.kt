package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity

data class GetMovieDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongToCollections: String?,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genres>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overView: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountries>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguages>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title:String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun GetMovieDetailsResponse.toEntity() = MovieDetailsEntity (
    id, budget, homepage, imdbId, originalLanguage, originalTitle, overView,
    posterPath, releaseDate, runtime, status, voteAverage, voteCount, title,
    genres.toEntity(), productionCompanies.toEntity(), productionCountries.toEntity(),
    spokenLanguages.toEntity()
)
