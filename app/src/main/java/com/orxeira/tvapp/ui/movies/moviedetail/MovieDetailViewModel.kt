package com.orxeira.tvapp.ui.movies.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orxeira.domain.Movie
import com.orxeira.tvapp.ui.common.ScopedViewModel
import com.orxeira.usecases.FindMovieById
import com.orxeira.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher
) :
    ScopedViewModel(uiDispatcher) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() {
            if (_movie.value == null) findMovie()
            return _movie
        }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    private fun findMovie() {
        launch {
            _movie.value = findMovieById.invoke(movieId)
            updateUi()
        }
    }

    fun onFavoriteClicked() {
        launch {
            _movie.value?.let {
                _movie.value = toggleMovieFavorite.invoke(it)
                updateUi()
            }
        }
    }

    private fun updateUi() {
        movie.value?.run {
            _title.value = title
            _overview.value = overview
            _url.value = backdropPath
            _favorite.value = favorite
        }
    }
}