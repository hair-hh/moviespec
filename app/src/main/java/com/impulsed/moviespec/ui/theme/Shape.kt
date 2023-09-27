package com.impulsed.moviespec.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import javax.annotation.concurrent.Immutable


@Immutable
data class MovieSpecShapes(
    val smallRoundCornerShape: Shape,
    val mediumRoundCornerShape: Shape,
    val largeRoundCornerShape: Shape,
)

val LocalMovieSpecShapes = staticCompositionLocalOf {
    MovieSpecShapes(
        smallRoundCornerShape = RoundedCornerShape(ZeroCornerSize),
        mediumRoundCornerShape = RoundedCornerShape(ZeroCornerSize),
        largeRoundCornerShape = RoundedCornerShape(ZeroCornerSize)
    )
}