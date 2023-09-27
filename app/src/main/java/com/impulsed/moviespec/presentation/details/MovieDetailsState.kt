package com.impulsed.moviespec.presentation.details

import com.impulsed.moviespec.domain.entity.base.ErrorRecord
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.presentation.base.ScreenState

data class MovieDetailsState (
    val screenState: ScreenState,
    val movieDetails: MovieDetailsEntity?,
    val error: ErrorRecord?
)