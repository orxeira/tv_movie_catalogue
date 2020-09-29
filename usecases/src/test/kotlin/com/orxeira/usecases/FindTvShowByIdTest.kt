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
class FindTvShowByIdTest {

    @Mock
    lateinit var tvShowRepository: TvShowRepository

    lateinit var findTvShowById: FindTvShowById

    @Before
    fun setUp() {
        findTvShowById = FindTvShowById(tvShowRepository)
    }

    @Test
    fun `invoke calls tv show repository`() {
        runBlocking {

            val tvShow = mockedTvShow.copy(id = 1)
            whenever(tvShowRepository.findById(1)).thenReturn(tvShow)

            val result = findTvShowById.invoke(1)

            Assert.assertEquals(tvShow, result)
        }
    }
}