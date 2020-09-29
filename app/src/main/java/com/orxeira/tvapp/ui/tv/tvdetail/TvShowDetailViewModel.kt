package com.orxeira.tvapp.ui.tv.tvdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orxeira.domain.Movie
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.ui.common.ScopedViewModel
import com.orxeira.usecases.FindTvShowById
import com.orxeira.usecases.ToggleTvShowFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TvShowDetailViewModel(
    private val tvShowId: Int,
    private val findTvShowById: FindTvShowById,
    private val toggleTvShowFavorite: ToggleTvShowFavorite,
    uiDispatcher: CoroutineDispatcher
) :
    ScopedViewModel(uiDispatcher) {

    private val _tvShow = MutableLiveData<TvShow>()
    val tvShow: LiveData<TvShow>
        get() {
            if (_tvShow.value == null) findTvShow()
            return _tvShow
        }

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    private fun findTvShow() {
        launch {
            _tvShow.value = findTvShowById.invoke(tvShowId)
            updateUi()
        }
    }

    private fun updateUi() {
        tvShow.value?.run {
            _name.value = name
            _overview.value = overview
            _url.value = backdropPath
            _favorite.value = favorite
        }
    }

    fun onFavoriteClicked() {
        launch {
            _tvShow.value?.let {
                _tvShow.value = toggleTvShowFavorite.invoke(it)
                updateUi()
            }
        }
    }
}
