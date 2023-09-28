package com.impulsed.moviespec.presentation.home

sealed class HomeIntent {
    object HandlePaginationDataError : HomeIntent()
    data class HandlePaginationAppendError(val message: String, val action: String) : HomeIntent()
}
