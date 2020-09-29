package com.orxeira.usecases

import com.nhaarman.mockitokotlin2.verify
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
class ToggleTvShowFavoriteTest {

    @Mock
    lateinit var tvShowsRepository: TvShowRepository

    lateinit var toggleTvShowFavorite: ToggleTvShowFavorite

    @Before
    fun setUp() {
        toggleTvShowFavorite = ToggleTvShowFavorite(tvShowsRepository)
    }

    @Test
    fun `invoke calls tv show repository`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(id = 1)

            val result = toggleTvShowFavorite.invoke(tvShow)

            verify(tvShowsRepository).update(result)
        }
    }

    @Test
    fun `favorite tv show becomes unfavorite`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(favorite = true)

            val result = toggleTvShowFavorite.invoke(tvShow)

            Assert.assertFalse(result.favorite)
        }
    }

    @Test
    fun `unfavorite tv show becomes favorite`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(favorite = false)

            val result = toggleTvShowFavorite.invoke(tvShow)

            Assert.assertTrue(result.favorite)
        }
    }
}