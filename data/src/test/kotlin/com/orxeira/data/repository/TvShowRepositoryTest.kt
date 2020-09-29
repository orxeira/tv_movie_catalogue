package com.orxeira.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.orxeira.data.source.RemoteDataSource
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.tvapp.testshared.mockedTvShow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest {

    @Mock
    lateinit var localDataSource: TvShowLocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var tvShowRepository: TvShowRepository

    private val apiKey = "1a2b3c4d"

    @Before
    fun setUp() {
        tvShowRepository =
            TvShowRepository(localDataSource, remoteDataSource, apiKey)
    }

    @Test
    fun `getPopularTvShows gets from local data source first`() {
        runBlocking {

            val localTvShows = listOf(mockedTvShow.copy(1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularTvShows()).thenReturn(localTvShows)

            val result = tvShowRepository.findPopularTvShows()

            Assert.assertEquals(localTvShows, result)
        }
    }

    @Test
    fun `getPopularTvShows saves remote data to local`() {
        runBlocking {

            val remoteTvShows = listOf(mockedTvShow.copy(2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularTvShows(any())).thenReturn(remoteTvShows)

            tvShowRepository.findPopularTvShows()

            verify(localDataSource).saveTvShows(remoteTvShows)
        }
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(tvShow)

            val result = tvShowRepository.findById(5)

            Assert.assertEquals(tvShow, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(id = 1)

            tvShowRepository.update(tvShow)

            verify(localDataSource).update(tvShow)
        }
    }

}