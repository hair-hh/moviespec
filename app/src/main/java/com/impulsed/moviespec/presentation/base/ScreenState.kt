package com.impulsed.moviespec.presentation.base

sealed class ScreenState {
    object Loading: ScreenState()
    object Success: ScreenState()
    object Error: ScreenState()
}
