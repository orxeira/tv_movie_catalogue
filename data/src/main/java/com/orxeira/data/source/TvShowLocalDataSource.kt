package com.orxeira.data.source

import com.orxeira.domain.TvShow

interface TvShowLocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveTvShows(tvShows: List<TvShow>)
    suspend fun getPopularTvShows(): List<TvShow>
    suspend fun findById(id: Int): TvShow
    suspend fun update(tvShow: TvShow)
}
