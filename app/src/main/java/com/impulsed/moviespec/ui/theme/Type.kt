package com.impulsed.moviespec.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.impulsed.moviespec.R
import javax.annotation.concurrent.Immutable

// Set of Material typography styles to start with
val GeoMatrix = FontFamily(
    Font(R.font.geomatrix, FontWeight.Normal),
    Font(R.font.geomatrix_bold, FontWeight.Bold),
    Font(R.font.geomatrix_medium, FontWeight.Medium)
)

@Immutable
data class MovieSpecTypography(
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val subTitle1: TextStyle,
    val subTitle2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle
)

val LocalMovieSpecTypography = staticCompositionLocalOf {
    MovieSpecTypography(
        title1 = TextStyle.Default,
        title2 = TextStyle.Default,
        title3 = TextStyle.Default,
        subTitle1 = TextStyle.Default,
        subTitle2 = TextStyle.Default,
        body1 = TextStyle.Default,
        body2 = TextStyle.Default,
        button = TextStyle.Default
    )
}