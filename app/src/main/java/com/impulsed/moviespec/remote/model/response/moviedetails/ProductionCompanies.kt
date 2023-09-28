package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName
import com.impulsed.moviespec.domain.entity.moviedetails.ProductionCompaniesEntity

data class ProductionCompanies(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)

fun ProductionCompanies.toEntity() = ProductionCompaniesEntity(id, logoPath, name, originCountry)

fun List<ProductionCompanies>.toEntity() = map { it.toEntity() }
