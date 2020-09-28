package com.orxeira.usecases

import com.orxeira.data.repository.MoviesRepository
import com.orxeira.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}