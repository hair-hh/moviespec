package com.impulsed.moviespec.presentation.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.impulsed.moviespec.R
import com.impulsed.moviespec.domain.entity.movies.MovieResultEntity
import com.impulsed.moviespec.presentation.base.ScreenState
import com.impulsed.moviespec.presentation.common.GetMoviesError
import com.impulsed.moviespec.presentation.common.HomeAppBar
import com.impulsed.moviespec.presentation.common.LoadingItem
import com.impulsed.moviespec.presentation.common.SnackbarView
import com.impulsed.moviespec.presentation.home.HomeIntent
import com.impulsed.moviespec.presentation.home.HomeViewModel
import com.impulsed.moviespec.presentation.utility.formatUrlForGlide
import com.impulsed.moviespec.presentation.utility.setPortrait
import com.impulsed.moviespec.presentation.utility.trimDateToYear
import com.impulsed.moviespec.ui.theme.MovieSpecTheme

@Composable
fun HomeScreen(
    openMovieDetails: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {


    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        context.setPortrait()
    }

    LaunchedEffect(homeViewModel.uiSideEffect()) {
        val messageHost = SnackbarView(this)
        homeViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                is HomeSideEffect.ShowSnackBar -> {
                    messageHost.showSnackBar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        message = uiSideEffect.message
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            HomeAppBar(
                title = stringResource(id = R.string.home_app_bar_title)
            )
        },
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            MovieListing(
                paddingValues = paddingValues,
                openMovieDetails = openMovieDetails,
                homeViewModel = homeViewModel
            )
        }
    )
}

@Composable
private fun MovieListing(
    paddingValues: PaddingValues,
    openMovieDetails: (Int) -> Unit,
    homeViewModel: HomeViewModel
) {

    val errorMessage: String = stringResource(id = R.string.home_screen_scroll_error)
    val action: String = stringResource(id = R.string.all_ok)
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlow = homeViewModel.uiState()
    val stateLifecycleAware = remember(lifecycleOwner, stateFlow) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val state by stateLifecycleAware.collectAsState(initial = homeViewModel.createInitialState())

    when (state.screenState) {
        is ScreenState.Loading -> {
            //do nothing
        }

        is ScreenState.Error -> {
            GetMoviesError { homeViewModel.initData() }
        }

        is ScreenState.Success -> {
            val lazyGameItems = state.movies?.collectAsLazyPagingItems()
            lazyGameItems?.let { moviesItems ->
                LazyVerticalGrid(
                    modifier = Modifier.padding(paddingValues),
                    columns = GridCells.Fixed(count = 2),
                    content = {
                        items(moviesItems.itemCount) { index ->
                            moviesItems[index]?.let {
                                MovieItem(movie = it, movieClick = openMovieDetails)
                            }
                        }

                        moviesItems.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item { LoadingItem() }
                                    item { LoadingItem() }
                                }

                                loadState.append is LoadState.Loading -> {
                                    item { LoadingItem() }
                                    item { LoadingItem() }
                                }

                                loadState.refresh is LoadState.Error -> {
                                    homeViewModel.intent(HomeIntent.HandlePaginationDataError)
                                }

                                loadState.append is LoadState.Error -> {
                                    homeViewModel.intent(
                                        HomeIntent.HandlePaginationAppendError(
                                            message = errorMessage,
                                            action = action
                                        )
                                    )
                                }
                            }
                        }
                    })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieItem(movie: MovieResultEntity, movieClick: (Int) -> Unit) {
    Card(
        elevation = 20.dp,
        backgroundColor = MovieSpecTheme.colors.background,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(290.dp)
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    movieClick(movie.id)
                }
            )
    ) {
        ConstraintLayout(modifier = Modifier.background(MovieSpecTheme.colors.background)) {
            val (image, title, rating) = createRefs()
            GlideImage(
                model = movie.image.formatUrlForGlide(),
                contentDescription = stringResource(
                    id = R.string.all_movies_content_description
                ),
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movie.title,
                style = MovieSpecTheme.typography.subTitle1,
                color = MovieSpecTheme.colors.primary,
                maxLines = 2,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(rating) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Text(
                    text = movie.rating.toString(),
                    style = MovieSpecTheme.typography.subTitle2,
                    color = MovieSpecTheme.colors.secondary,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.star_ic),
                    contentDescription = stringResource(id = R.string.all_movies_content_description),
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = movie.releaseDate.trimDateToYear(),
                    style = MovieSpecTheme.typography.subTitle2,
                    color = MovieSpecTheme.colors.secondary,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        }
    }
}

@Composable
@Preview
private fun ShowMovieItem() {
    MovieItem(
        movie = MovieResultEntity(
            id = 123,
            image = "http://image.tmdb.org/t/p/w300/kdPMUMJzyYAc4roD52qavX0nLIC.jpg",
            title = "Hand",
            rating = 5.0F,
            releaseDate = "2023-08-23"
        ), movieClick = {}
    )
}