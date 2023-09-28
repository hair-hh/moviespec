package com.impulsed.moviespec.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MovieSpecTheme(content: @Composable () -> Unit) {
    val localMovieSpecColors = MovieSpecColors(
        primary = PrimaryColor,
        primaryVariant = PrimaryVariantColor,
        secondary = SecondaryColor,
        background = Color.Black,
        onBackground = Color.White,
        surface = SurfaceColor,
        onDisabled = DisabledColor
    )

    val localMovieSpecTypography = MovieSpecTypography(
        title1 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 24.sp,
            lineHeight = 28.8.sp,
            fontWeight = FontWeight.W700
        ),
        title2 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 24.sp,
            lineHeight = 26.4.sp,
            fontWeight = FontWeight.W700
        ),
        title3 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W700
        ),
        subTitle1 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 18.sp,
            lineHeight = 21.6.sp,
            fontWeight = FontWeight.W700
        ),
        subTitle2 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
            fontWeight = FontWeight.W700
        ),
        body1 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 18.sp,
            lineHeight = 21.6.sp,
            fontWeight = FontWeight.W400
        ),
        body2 = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
            fontWeight = FontWeight.W400
        ),
        button = TextStyle(
            fontFamily = GeoMatrix,
            fontSize = 18.sp,
            lineHeight = 21.6.sp,
            fontWeight = FontWeight.W700
        )
    )

    val localMovieSpecShapes = MovieSpecShapes(
        smallRoundCornerShape = RoundedCornerShape(4.dp),
        mediumRoundCornerShape = RoundedCornerShape(6.dp),
        largeRoundCornerShape = RoundedCornerShape(8.dp)
    )

    CompositionLocalProvider(
        LocalMovieSpecTypography provides localMovieSpecTypography,
        LocalMovieSpecColors provides localMovieSpecColors,
        LocalMovieSpecShapes provides localMovieSpecShapes
    ) {
        MaterialTheme(content = content)
    }
}

object MovieSpecTheme {
    val colors: MovieSpecColors
        @Composable
        @ReadOnlyComposable
        @NonRestartableComposable
        get() = LocalMovieSpecColors.current

    val shapes: MovieSpecShapes
        @Composable
        @ReadOnlyComposable
        @NonRestartableComposable
        get() = LocalMovieSpecShapes.current

    val typography: MovieSpecTypography
        @Composable
        @ReadOnlyComposable
        @NonRestartableComposable
        get() = LocalMovieSpecTypography.current
}