package com.impulsed.moviespec.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.impulsed.moviespec.domain.entity.base.ErrorRecord
import com.impulsed.moviespec.domain.entity.movies.MovieResultEntity
import com.impulsed.moviespec.data.MoviesSource
import com.impulsed.moviespec.presentation.Destinations.HomeAgs.moviesPage
import com.impulsed.moviespec.presentation.base.BaseViewModel
import com.impulsed.moviespec.presentation.base.ScreenState
import com.impulsed.moviespec.presentation.home.ui.HomeState
import com.impulsed.moviespec.presentation.home.ui.HomeSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesSource: MoviesSource
): BaseViewModel<HomeState, HomeSideEffect>(savedStateHandle) {
    override fun createInitialState(): HomeState = HomeState(ScreenState.Loading, null, null)

    override fun initData() = intent {
        val moviesPage: Int? = savedStateHandle[moviesPage]
        if (moviesPage == null || moviesPage ==0) {
            val result = getAllMovies()
            reduce {
                state.copy(
                    screenState = ScreenState.Success,
                    movies = result.flow,
                    error = null)
            }
        }


    }

    fun intent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.HandlePaginationDataError ->
                handlePaginationDataError()
            is HomeIntent.HandlePaginationAppendError -> {
                handlePaginationAppendError(message = intent.message, intent.action)
            }
        }
    }

    private fun handlePaginationDataError() = intent {
        reduce {
            state.copy(screenState = ScreenState.Error,
                movies = null,
                error = ErrorRecord.ServerError
            )
        }
    }

    private fun handlePaginationAppendError(message: String, action: String) = intent {
        postSideEffect(HomeSideEffect.ShowSnackBar(message = message, action = action))
    }

    private fun getAllMovies() : Pager<Int, MovieResultEntity> {
        return Pager(PagingConfig(20)) {
            moviesSource
        }
    }
}