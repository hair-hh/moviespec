package com.impulsed.moviespec.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.impulsed.moviespec.presentation.Destinations.MovieDetailsArgs.movieId
import com.impulsed.moviespec.domain.interactor.MovieDetailsUseCase
import com.impulsed.moviespec.presentation.base.BaseViewModel
import com.impulsed.moviespec.presentation.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: MovieDetailsUseCase
) : BaseViewModel<MovieDetailsState, MovieDetailsSideEffect>(savedStateHandle) {
    override fun createInitialState(): MovieDetailsState =
        MovieDetailsState(ScreenState.Loading, null, null)

    override fun initData() {
        super.initData()
        val movieId: Int? = savedStateHandle[movieId]
        if (movieId == null || movieId == 0) {
            handleMovieIdError()
        } else {
            getMovieDetails(movieId = movieId)
        }
    }

    private fun getMovieDetails(movieId: Int) = intent {
        getMovieDetailsUseCase.invoke(
            MovieDetailsUseCase.RequestValue(movieId = movieId)
        ).collect { record ->
            if (record.data != null) {
                reduce {
                    state.copy(
                        screenState = ScreenState.Success,
                        movieDetails = record.data,
                        error = null
                    )
                }
            } else {
                reduce {
                    state.copy(
                        screenState = ScreenState.Error,
                        movieDetails = null,
                        error = record.error
                    )
                }
                postSideEffect(MovieDetailsSideEffect.ShowMovieDetailsErrorToast)
            }
        }
    }

    private fun handleMovieIdError() = intent {
        postSideEffect(MovieDetailsSideEffect.ShowMovieIdErrorToast)
    }
}