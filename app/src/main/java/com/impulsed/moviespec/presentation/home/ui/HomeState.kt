package com.impulsed.moviespec.presentation.home.ui

import androidx.paging.PagingData
import com.impulsed.moviespec.domain.entity.base.ErrorRecord
import com.impulsed.moviespec.domain.entity.movies.MovieResultEntity
import com.impulsed.moviespec.presentation.base.ScreenState
import kotlinx.coroutines.flow.Flow

data class HomeState (
    val screenState: ScreenState,
    val movies: Flow<PagingData<MovieResultEntity>>?,
    val error: ErrorRecord?
)