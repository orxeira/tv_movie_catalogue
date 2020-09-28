package com.orxeira.tvapp.data.database

import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.data.toDomainTvShow
import com.orxeira.tvapp.data.toRoomTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvShowRoomDataSource(db: MovieDatabase) : TvShowLocalDataSource {

    private val tvShowDao = db.tvShowDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { tvShowDao.tvShowCount() <= 0 }

    override suspend fun saveTvShows(tvShows: List<TvShow>) {
        withContext(Dispatchers.IO) { tvShowDao.insertTvShows(tvShows.map { it.toRoomTvShow() }) }
    }

    override suspend fun getPopularTvShows(): List<TvShow> = withContext(Dispatchers.IO) {
        tvShowDao.getAll().map { it.toDomainTvShow() }
    }

    override suspend fun findById(id: Int): TvShow = withContext(Dispatchers.IO) {
        tvShowDao.findById(id).toDomainTvShow()
    }

    override suspend fun update(tvShow: TvShow) {
        withContext(Dispatchers.IO) { tvShowDao.updateTvShow(tvShow.toRoomTvShow()) }
    }

}