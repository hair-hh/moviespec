package com.impulsed.moviespec.presentation.common

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.impulsed.moviespec.ui.theme.MovieSpecTheme

@Composable
fun HomeAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MovieSpecTheme.typography.title1,
                color = MovieSpecTheme.colors.onBackground
            )
        },
        modifier = modifier,
        backgroundColor = MovieSpecTheme.colors.primary,
    )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    HomeAppBar(title = "Epic World")
}