package com.orxeira.tvapp.ui.movies.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.domain.Movie
import com.orxeira.tvapp.testshared.mockedMovie
import com.orxeira.tvapp.ui.FakeLocalDataSource
import com.orxeira.tvapp.ui.defaultFakeMovies
import com.orxeira.tvapp.ui.initMockedDi
import com.orxeira.tvapp.ui.movies.moviemain.MovieMainViewModel
import com.orxeira.usecases.GetPopularMovies
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
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieObserver: Observer<List<Movie>>

    private lateinit var vm: MovieMainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MovieMainViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.movies.observeForever(movieObserver)

        vm.onCoarsePermissionRequested()

        verify(movieObserver).onChanged(defaultFakeMovies)
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<MovieLocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.movies.observeForever(movieObserver)

        vm.onCoarsePermissionRequested()

        verify(movieObserver).onChanged(fakeLocalMovies)
    }
}