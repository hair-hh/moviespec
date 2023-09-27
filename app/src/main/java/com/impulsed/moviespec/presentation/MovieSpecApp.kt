package com.impulsed.moviespec.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.impulsed.moviespec.presentation.Destinations.HOME
import com.impulsed.moviespec.presentation.Destinations.MOVIE_DETAILS
import com.impulsed.moviespec.presentation.Destinations.MovieDetailsArgs.movieId
import com.impulsed.moviespec.presentation.details.MovieDetailsScreen
import com.impulsed.moviespec.presentation.home.ui.HomeScreen

@Composable
fun MovieSpecApp() {

    val navController = rememberNavController()
    val actions = remember(navController) {
        Actions(navController)
    }
    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME) {
            HomeScreen(openGameDetails = actions.openMovieDetails)
        }

        composable(
            "$MOVIE_DETAILS/{$movieId}",
            arguments = listOf(
                navArgument(movieId) { type = NavType.IntType }
            )
        ) {
            MovieDetailsScreen(
                navigateBack = actions.navigateBack
            )
        }
    }
}