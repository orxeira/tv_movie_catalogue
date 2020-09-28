package com.orxeira.tvapp.ui.movies.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.domain.Movie
import com.orxeira.tvapp.testshared.mockedMovie
import com.orxeira.tvapp.ui.FakeLocalDataSource
import com.orxeira.tvapp.ui.defaultFakeMovies
import com.orxeira.tvapp.ui.initMockedDi
import com.orxeira.tvapp.ui.movies.moviedetail.MovieDetailViewModel
import com.orxeira.usecases.FindMovieById
import com.orxeira.usecases.ToggleMovieFavorite
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
class MovieDetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<Movie>

    private lateinit var vm: MovieDetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> MovieDetailViewModel(id, get(), get(), get()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        localDataSource = get<MovieLocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @Test
    fun `observing LiveData finds the movie`() {
        vm.movie.observeForever(observer)

        verify(observer).onChanged(mockedMovie.copy(1))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.movie.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            Assert.assertTrue(localDataSource.findById(1).favorite)
        }
    }
}