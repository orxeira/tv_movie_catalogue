package com.orxeira.usecases

import com.orxeira.data.repository.MoviesRepository
import com.orxeira.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.findPopularMovies()
}