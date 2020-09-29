package com.orxeira.usecases

import com.orxeira.data.repository.TvShowRepository
import com.orxeira.domain.TvShow

class ToggleTvShowFavorite(private val tvShowRepository: TvShowRepository) {
    suspend fun invoke(tvShow: TvShow): TvShow = with(tvShow) {
        copy(favorite = !favorite).also { tvShowRepository.update(it) }
    }
}