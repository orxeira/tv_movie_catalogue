package com.orxeira.tvapp.ui.movies.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.orxeira.domain.Movie
import com.orxeira.tvapp.testshared.mockedMovie
import com.orxeira.tvapp.ui.common.Event
import com.orxeira.tvapp.ui.common.EventObserver
import com.orxeira.tvapp.ui.movies.moviemain.MovieMainViewModel
import com.orxeira.usecases.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var movieObserver: Observer<List<Movie>>

    @Mock
    lateinit var requestLocationPermissionObserver: EventObserver<Unit>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    private lateinit var vm: MovieMainViewModel

    @Before
    fun setUp() {
        vm = MovieMainViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.requestLocationPermission.observeForever(requestLocationPermissionObserver)

        verify(requestLocationPermissionObserver).onChanged(Event(Unit))
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.loading.observeForever(loadingObserver)

            vm.onCoarsePermissionRequested()

            verify(loadingObserver).onChanged(true)
        }
    }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.movies.observeForever(movieObserver)

            vm.onCoarsePermissionRequested()

            verify(movieObserver).onChanged(movies)
        }
    }
}