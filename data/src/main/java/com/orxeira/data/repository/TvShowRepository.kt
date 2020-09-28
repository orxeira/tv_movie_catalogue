package com.orxeira.data.repository

import com.orxeira.data.source.RemoteDataSource
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.domain.TvShow

class TvShowRepository(
    private val localDataSource: TvShowLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun findPopularTvShows(): List<TvShow> {
        if (localDataSource.isEmpty()) {
            val tvShows =
                remoteDataSource.getPopularTvShows(apiKey)
            localDataSource.saveTvShows(tvShows)
        }

        return localDataSource.getPopularTvShows()
    }

    suspend fun findById(id: Int): TvShow = localDataSource.findById(id)

    suspend fun update(tvShow: TvShow) = localDataSource.update(tvShow)

}