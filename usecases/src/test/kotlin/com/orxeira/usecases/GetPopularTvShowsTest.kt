package com.orxeira.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.orxeira.data.repository.TvShowRepository
import com.orxeira.tvapp.testshared.mockedTvShow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPopularTvShowsTest {

    @Mock
    lateinit var tvShowsRepository: TvShowRepository

    lateinit var getPopularTvShows: GetPopularTvShows

    @Before
    fun setUp() {
        getPopularTvShows = GetPopularTvShows(tvShowsRepository)
    }

    @Test
    fun `invoke calls tv show repository`() {
        runBlocking {

            val tvShows = listOf(mockedTvShow.copy(id = 1))
            whenever(tvShowsRepository.findPopularTvShows()).thenReturn(tvShows)

            val result = getPopularTvShows.invoke()

            Assert.assertEquals(tvShows, result)
        }
    }
}
