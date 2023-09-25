package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName
import com.impulsed.moviespec.domain.entity.moviedetails.ProductionCountriesEntity

data class ProductionCountries(
    @SerializedName("iso_3166_1")
    val iso: String,
    @SerializedName("name")
    val name: String
)

fun ProductionCountries.toEntity() = ProductionCountriesEntity(iso, name)

fun List<ProductionCountries>.toEntity() = map { it.toEntity() }
