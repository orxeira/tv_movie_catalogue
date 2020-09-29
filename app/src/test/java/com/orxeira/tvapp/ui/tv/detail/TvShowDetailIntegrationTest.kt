package com.orxeira.tvapp.ui.tv.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.testshared.mockedTvShow
import com.orxeira.tvapp.ui.FakeTvShowLocalDataSource
import com.orxeira.tvapp.ui.defaultFakeTvShows
import com.orxeira.tvapp.ui.initMockedDi
import com.orxeira.tvapp.ui.tv.tvdetail.TvShowDetailViewModel
import com.orxeira.usecases.FindTvShowById
import com.orxeira.usecases.ToggleTvShowFavorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowDetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<TvShow>

    private lateinit var vm: TvShowDetailViewModel
    private lateinit var localDataSource: FakeTvShowLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> TvShowDetailViewModel(id, get(), get(), get()) }
            factory { FindTvShowById(get()) }
            factory { ToggleTvShowFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        localDataSource = get<TvShowLocalDataSource>() as FakeTvShowLocalDataSource
        localDataSource.tvShows = defaultFakeTvShows
    }

    @Test
    fun `observing LiveData finds the tvShow`() {
        vm.tvShow.observeForever(observer)

        verify(observer).onChanged(mockedTvShow.copy(1))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.tvShow.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            Assert.assertTrue(localDataSource.findById(1).favorite)
        }
    }
}