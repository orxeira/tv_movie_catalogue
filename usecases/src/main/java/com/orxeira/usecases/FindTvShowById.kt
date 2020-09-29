package com.orxeira.usecases

import com.orxeira.data.repository.TvShowRepository
import com.orxeira.domain.TvShow

class FindTvShowById(private val tvShowRepository: TvShowRepository) {
    suspend fun invoke(id: Int): TvShow = tvShowRepository.findById(id)
}