package com.orxeira.data.repository

import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.data.source.RemoteDataSource
import com.orxeira.domain.Movie

class MoviesRepository(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {
    suspend fun findPopularMovies(): List<Movie> {

        if (localDataSource.isEmpty()) {
            val movies =
                remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}