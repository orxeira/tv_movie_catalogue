package com.orxeira.tvapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orxeira.tvapp.ui.common.Event

class MainViewModel : ViewModel() {

    enum class Navigation {
        Movies(),
        TvShows()
    }

    private val _navigation = MutableLiveData<Event<Navigation>>()
    val navigation: LiveData<Event<Navigation>>
        get() = _navigation

    fun movies() {
        _navigation.value = Event(Navigation.Movies)
    }

    fun tvShows() {
        _navigation.value = Event(Navigation.TvShows)
    }

}