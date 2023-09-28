package com.impulsed.moviespec.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.impulsed.moviespec.domain.entity.movies.MovieResultEntity
import com.impulsed.moviespec.domain.repository.MovieRepository
import javax.inject.Inject

class MoviesSource @Inject constructor(private val movieRepository: MovieRepository) :
    PagingSource<Int, MovieResultEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResultEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResultEntity> {
        val nextPage = params.key ?: 1
        val moviesResponse = movieRepository.getAllMovies(nextPage)
        return if (moviesResponse.data == null) {
            LoadResult.Error(Exception(moviesResponse.error.toString()))
        } else {
            LoadResult.Page(
                data = moviesResponse.data.moviesEntities,
                prevKey = if (nextPage == 1) null else nextPage-1,
                nextKey = nextPage.plus(1)
            )
        }

    }
}