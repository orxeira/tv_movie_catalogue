package com.orxeira.tvapp.data.server

import com.orxeira.tvapp.data.server.model.MovieDbResult
import com.orxeira.tvapp.data.server.model.TvShowDbResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDbResult

    @GET("discover/tv?sort_by=popularity.desc")
    suspend fun listPopularTvShowsAsync(
        @Query("api_key") apiKey: String
    ): TvShowDbResult
}