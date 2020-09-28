package com.orxeira.tvapp.data.server

import com.orxeira.data.source.RemoteDataSource
import com.orxeira.domain.Movie
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.data.toDomainMovie
import com.orxeira.tvapp.data.toDomainTvShow

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }

    override suspend fun getPopularTvShows(apiKey: String): List<TvShow> =
        theMovieDb.service
            .listPopularTvShowsAsync(apiKey)
            .results
            .map { it.toDomainTvShow() }
}