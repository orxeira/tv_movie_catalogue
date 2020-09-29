package com.orxeira.tvapp.ui.tv.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.testshared.mockedTvShow
import com.orxeira.tvapp.ui.FakeTvShowLocalDataSource
import com.orxeira.tvapp.ui.defaultFakeTvShows
import com.orxeira.tvapp.ui.initMockedDi
import com.orxeira.tvapp.ui.tv.tvmain.TvShowMainViewModel
import com.orxeira.usecases.GetPopularTvShows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var tvShowObserver: Observer<List<TvShow>>

    private lateinit var vm: TvShowMainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { TvShowMainViewModel(get(), get()) }
            factory { GetPopularTvShows(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.tvShows.observeForever(tvShowObserver)

        verify(tvShowObserver).onChanged(defaultFakeTvShows)
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalTvShows = listOf(mockedTvShow.copy(10), mockedTvShow.copy(11))
        val localDataSource = get<TvShowLocalDataSource>() as FakeTvShowLocalDataSource
        localDataSource.tvShows = fakeLocalTvShows
        vm.tvShows.observeForever(tvShowObserver)

        verify(tvShowObserver).onChanged(fakeLocalTvShows)
    }
}