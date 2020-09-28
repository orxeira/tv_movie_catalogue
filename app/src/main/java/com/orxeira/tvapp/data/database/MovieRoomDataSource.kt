package com.orxeira.tvapp.data.database

import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.domain.Movie
import com.orxeira.tvapp.data.toDomainMovie
import com.orxeira.tvapp.data.toRoomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRoomDataSource(db: MovieDatabase) : MovieLocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { movieDao.movieCount() <= 0 }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) { movieDao.insertMovies(movies.map { it.toRoomMovie() }) }
    }

    override suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun findById(id: Int): Movie = withContext(Dispatchers.IO) {
        movieDao.findById(id).toDomainMovie()
    }

    override suspend fun update(movie: Movie) {
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) }
    }
}