package com.orxeira.usecases

import com.orxeira.data.repository.TvShowRepository
import com.orxeira.domain.TvShow

class GetPopularTvShows(private val moviesRepository: TvShowRepository) {
    suspend fun invoke(): List<TvShow> = moviesRepository.findPopularTvShows()
}