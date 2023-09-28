package com.impulsed.moviespec.presentation

import androidx.navigation.NavHostController
import com.impulsed.moviespec.presentation.Destinations.HOME
import com.impulsed.moviespec.presentation.Destinations.MOVIE_DETAILS


object Destinations {
    const val HOME = "home"
    const val MOVIE_DETAILS = "movieDetails"

    object MovieDetailsArgs {
        const val movieId = "movieId"
    }

    object HomeAgs {
        const val moviesPage = "moviesPage"
    }
}
class Actions(navHostController: NavHostController) {

    val openMovieDetails: (Int) -> Unit = {movieId ->
        navHostController.popBackStack(route = HOME, inclusive = false, saveState = true)
        navHostController.navigate("$MOVIE_DETAILS/$movieId")
    }

    val navigateBack: () -> Unit = {
        navHostController.navigateUp()
    }
}