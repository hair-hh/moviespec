package com.impulsed.moviespec.presentation.details

sealed class MovieDetailsSideEffect {
    object ShowMovieIdErrorToast: MovieDetailsSideEffect()
    object ShowMovieDetailsErrorToast: MovieDetailsSideEffect()
}
