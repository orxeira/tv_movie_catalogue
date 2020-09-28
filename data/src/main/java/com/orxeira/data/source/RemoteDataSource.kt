package com.orxeira.data.source

import com.orxeira.domain.Movie
import com.orxeira.domain.TvShow

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
    suspend fun getPopularTvShows(apiKey: String): List<TvShow>
}