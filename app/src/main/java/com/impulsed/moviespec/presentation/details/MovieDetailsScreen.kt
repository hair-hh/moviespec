package com.impulsed.moviespec.presentation.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.impulsed.moviespec.R
import com.impulsed.moviespec.domain.entity.moviedetails.MovieDetailsEntity
import com.impulsed.moviespec.presentation.base.ScreenState
import com.impulsed.moviespec.presentation.common.LoadingView
import com.impulsed.moviespec.presentation.utility.formatUrlForPosterGlide
import com.impulsed.moviespec.presentation.utility.setPortrait
import com.impulsed.moviespec.ui.theme.MovieSpecTheme

@Composable
fun MovieDetailsScreen(
    navigateBack: () -> Unit,
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val stateFlow = movieDetailsViewModel.uiState()
    val stateLifecycleAware = remember(lifecycleOwner, stateFlow) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val state by stateLifecycleAware.collectAsState(initial = movieDetailsViewModel.createInitialState())

    val movieIdError = stringResource(id = R.string.movie_details_invalid_movie_id)
    val genericError = stringResource(id = R.string.all_generic_error)

    LaunchedEffect(key1 = true) {
        context.setPortrait()
    }

    LaunchedEffect(movieDetailsViewModel.uiSideEffect()) {
        movieDetailsViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                is MovieDetailsSideEffect.ShowMovieIdErrorToast -> {
                    Toast.makeText(context, movieIdError, Toast.LENGTH_LONG).show()
                    navigateBack.invoke()
                }

                is MovieDetailsSideEffect.ShowMovieDetailsErrorToast -> {
                    Toast.makeText(context, genericError, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    when (state.screenState) {
        is ScreenState.Loading -> {
            LoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            )
        }

        is ScreenState.Error -> {
            navigateBack.invoke()
        }

        is ScreenState.Success -> {
            state.movieDetails?.let {
                MovieDetails(
                    modifier = Modifier.systemBarsPadding(),
                    movieDetails = it
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetails(
    modifier: Modifier,
    movieDetails: MovieDetailsEntity
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(MovieSpecTheme.colors.onDisabled)
    ) {
        ConstraintLayout {
            val (movieImage) = createRefs()
            GlideImage(
                model = movieDetails.posterPath.formatUrlForPosterGlide(),
                contentDescription = stringResource(id = R.string.all_movies_content_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .graphicsLayer {
                        shadowElevation = 50.dp.toPx()
                    }
                    .constrainAs(movieImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.FillWidth
            )
        }
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 30.dp, end = 16.dp),
                text = movieDetails.title,
                style = MovieSpecTheme.typography.title1,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = movieDetails.genres.joinToString(separator = " ") { it.name },
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (dateLabel, dateValue, runtimeLabel, runtimeValue, ratingLabel, ratingValue) = createRefs()
                Row(modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(dateLabel) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }) {
                    Text(
                        text = stringResource(id = R.string.movie_details_released),
                        style = MovieSpecTheme.typography.subTitle2,
                        color = MovieSpecTheme.colors.background
                    )
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        imageVector = Icons.Filled.Update,
                        contentDescription = stringResource(id = R.string.movie_details_calendar_date),
                        tint = MovieSpecTheme.colors.secondary
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(dateValue) {
                        top.linkTo(dateLabel.bottom, margin = 8.dp)
                        start.linkTo(dateLabel.start)
                        end.linkTo(dateLabel.end)
                    },
                    text = movieDetails.releaseDate,
                    textAlign = TextAlign.Center,
                    color = MovieSpecTheme.colors.background,
                    style = MovieSpecTheme.typography.body2,
                )
                Row(modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(runtimeLabel) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(dateLabel.end, margin = 16.dp)
                        end.linkTo(ratingLabel.start, margin = 16.dp)
                    }) {
                    Text(
                        text = stringResource(id = R.string.movie_details_runtime),
                        style = MovieSpecTheme.typography.subTitle2,
                        color = MovieSpecTheme.colors.background
                    )
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        imageVector = Icons.Filled.Timeline,
                        contentDescription = stringResource(id = R.string.all_movies_content_description),
                        tint = MovieSpecTheme.colors.secondary
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(runtimeValue) {
                        top.linkTo(runtimeLabel.bottom, margin = 8.dp)
                        start.linkTo(runtimeLabel.start)
                        end.linkTo(runtimeLabel.end)
                    },
                    text = movieDetails.runtime.toString(),
                    textAlign = TextAlign.Center,
                    color = MovieSpecTheme.colors.background,
                    style = MovieSpecTheme.typography.body2,
                )
                Row(modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(ratingLabel) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }) {
                    Text(
                        text = stringResource(id = R.string.movie_details_rating),
                        style = MovieSpecTheme.typography.subTitle2,
                        color = MovieSpecTheme.colors.background
                    )
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        imageVector = Icons.Filled.StarRate,
                        contentDescription = stringResource(id = R.string.all_movies_content_description),
                        tint = MovieSpecTheme.colors.secondary
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(ratingValue) {
                        top.linkTo(ratingLabel.bottom, margin = 8.dp)
                        start.linkTo(ratingLabel.start)
                        end.linkTo(ratingLabel.end)
                    },
                    text = movieDetails.voteAverage.toString(),
                    textAlign = TextAlign.Center,
                    color = MovieSpecTheme.colors.background,
                    style = MovieSpecTheme.typography.body2,
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_about),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            ExpandableDescription(description = movieDetails.overView)
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_homepage),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = movieDetails.homepage,
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_budget),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = movieDetails.budget.toString(),
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_languages),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = movieDetails.spokenLanguages.joinToString { it.name },
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_production_companies),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = movieDetails.productionCompanies.joinToString { it.name },
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = stringResource(id = R.string.movie_details_production_countries),
                style = MovieSpecTheme.typography.subTitle2,
                color = MovieSpecTheme.colors.background
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
                text = movieDetails.productionCountries.joinToString { it.name },
                style = MovieSpecTheme.typography.body2,
                color = MovieSpecTheme.colors.background
            )
    }
}

@Composable
private fun ExpandableDescription(modifier: Modifier = Modifier, description: String) {

    var shouldShowMore by remember {
        mutableStateOf(DescriptionStatus.DEFAULT)
    }
    var maxLines by remember {
        mutableIntStateOf(4)
    }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
            text = description,
            style = MovieSpecTheme.typography.body2,
            color = MovieSpecTheme.colors.background,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            onTextLayout = {
                if (it.lineCount == 4 && it.isLineEllipsized(4 - 1)) {
                    shouldShowMore = DescriptionStatus.SHOW_MORE
                } else if (it.lineCount > 4) {
                    shouldShowMore = DescriptionStatus.SHOW_LESS
                }
            }
        )
        when (shouldShowMore) {
            DescriptionStatus.SHOW_MORE -> {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            maxLines = Int.MAX_VALUE
                        },
                    text = stringResource(id = R.string.movie_details_about_show_more),
                    style = MovieSpecTheme.typography.body2,
                    textDecoration = TextDecoration.Underline,
                    color = MovieSpecTheme.colors.primary
                )
            }

            DescriptionStatus.SHOW_LESS -> {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            maxLines = 4
                        },
                    text = stringResource(id = R.string.movie_details_about_show_less),
                    style = MovieSpecTheme.typography.body2,
                    textDecoration = TextDecoration.Underline,
                    color = MovieSpecTheme.colors.primary
                )
            }

            else -> {
                //do nothing
            }
        }
    }
}