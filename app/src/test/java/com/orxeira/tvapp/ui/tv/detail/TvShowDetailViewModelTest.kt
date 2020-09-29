package com.orxeira.tvapp.ui.tv.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.testshared.mockedTvShow
import com.orxeira.tvapp.ui.tv.tvdetail.TvShowDetailViewModel
import com.orxeira.usecases.FindTvShowById
import com.orxeira.usecases.ToggleTvShowFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findTvShowById: FindTvShowById

    @Mock
    lateinit var toggleTvShowFavorite: ToggleTvShowFavorite

    @Mock
    lateinit var observer: Observer<TvShow>

    private lateinit var vm: TvShowDetailViewModel

    @Before
    fun setUp() {
        vm = TvShowDetailViewModel(1, findTvShowById, toggleTvShowFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the tvShow`() {

        runBlocking {
            val tvShow = mockedTvShow.copy(id = 1)
            whenever(findTvShowById.invoke(1)).thenReturn(tvShow)

            vm.tvShow.observeForever(observer)

            verify(observer).onChanged(tvShow)
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val tvShow = mockedTvShow.copy(id = 1)
            whenever(findTvShowById.invoke(1)).thenReturn(tvShow)
            whenever(toggleTvShowFavorite.invoke(tvShow)).thenReturn(tvShow.copy(favorite = !tvShow.favorite))
            vm.tvShow.observeForever(observer)

            vm.onFavoriteClicked()

            verify(toggleTvShowFavorite).invoke(tvShow)
        }
    }
}