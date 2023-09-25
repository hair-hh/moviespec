package com.impulsed.moviespec.remote.model.response.moviedetails

import com.google.gson.annotations.SerializedName
import com.impulsed.moviespec.domain.entity.moviedetails.SpokenLanguagesEntity

data class SpokenLanguages(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso: String,
    @SerializedName("name")
    val name: String
)

fun SpokenLanguages.toEntity() = SpokenLanguagesEntity(englishName, iso, name)

fun List<SpokenLanguages>.toEntity() = map { it.toEntity() }
