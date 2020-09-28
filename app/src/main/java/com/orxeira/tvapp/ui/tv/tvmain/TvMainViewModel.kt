package com.orxeira.tvapp.ui.tv.tvmain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.ui.common.Event
import com.orxeira.tvapp.ui.common.ScopedViewModel
import com.orxeira.usecases.GetPopularTvShows
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TvMainViewModel(
    private val getPopularTvShows: GetPopularTvShows,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _tvShows = MutableLiveData<List<TvShow>>()
    val tvShows: LiveData<List<TvShow>>
        get() {
            if (_tvShows.value == null) {
                fetchTvShows()
            }
            return _tvShows
        }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToTvShow = MutableLiveData<Event<Int>>()
    val navigateToTvShow: LiveData<Event<Int>> get() = _navigateToTvShow

    init {
        initScope()
    }

    private fun fetchTvShows() {
        launch {
            _loading.value = true
            _tvShows.value = getPopularTvShows.invoke()
            _loading.value = false
        }
    }

    fun onTvShowClicked(tvShow: TvShow) {
        _navigateToTvShow.value = Event(tvShow.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}