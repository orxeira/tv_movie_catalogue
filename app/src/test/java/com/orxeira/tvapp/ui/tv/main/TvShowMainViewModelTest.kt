package com.orxeira.tvapp.ui.tv.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.testshared.mockedTvShow
import com.orxeira.tvapp.ui.tv.tvmain.TvShowMainViewModel
import com.orxeira.usecases.GetPopularTvShows
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowMainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularTvShow: GetPopularTvShows

    @Mock
    lateinit var tvShowObserver: Observer<List<TvShow>>


    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    private lateinit var vm: TvShowMainViewModel

    @Before
    fun setUp() {
        vm = TvShowMainViewModel(getPopularTvShow, Dispatchers.Unconfined)
    }


    @Test
    fun `after starting, loading is shown`() {
        runBlocking {
            val tvShows = listOf(mockedTvShow.copy(id = 1))
            whenever(getPopularTvShow.invoke()).thenReturn(tvShows)

            vm.loading.observeForever(loadingObserver)
            vm.tvShows.observeForever(tvShowObserver)

            verify(loadingObserver).onChanged(true)
        }
    }

    @Test
    fun `after starting, getPopularTvShow is called`() {

        runBlocking {
            val tvShows = listOf(mockedTvShow.copy(id = 1))
            whenever(getPopularTvShow.invoke()).thenReturn(tvShows)

            vm.tvShows.observeForever(tvShowObserver)


            verify(tvShowObserver).onChanged(tvShows)
        }
    }
}