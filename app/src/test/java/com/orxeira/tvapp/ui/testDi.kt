package com.orxeira.tvapp.ui

import com.orxeira.data.repository.PermissionChecker
import com.orxeira.data.source.LocationDataSource
import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.data.source.RemoteDataSource
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.domain.Movie
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.dataModule
import com.orxeira.tvapp.testshared.mockedMovie
import com.orxeira.tvapp.testshared.mockedTvShow
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<MovieLocalDataSource> { FakeMovieLocalDataSource() }
    single<TvShowLocalDataSource> { FakeTvShowLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)

val defaultFakeTvShows = listOf(
    mockedTvShow.copy(1),
    mockedTvShow.copy(2),
    mockedTvShow.copy(3),
    mockedTvShow.copy(4)
)

class FakeMovieLocalDataSource : MovieLocalDataSource {

    var movies: List<Movie> = emptyList()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun getPopularMovies(): List<Movie> = movies

    override suspend fun findById(id: Int): Movie = movies.first { it.id == id }

    override suspend fun update(movie: Movie) {
        movies = movies.filterNot { it.id == movie.id } + movie
    }
}

class FakeTvShowLocalDataSource : TvShowLocalDataSource {

    var tvShows: List<TvShow> = emptyList()

    override suspend fun isEmpty() = tvShows.isEmpty()

    override suspend fun saveTvShows(tvShows: List<TvShow>) {
        this.tvShows = tvShows
    }

    override suspend fun getPopularTvShows(): List<TvShow> = tvShows

    override suspend fun findById(id: Int): TvShow = tvShows.first { it.id == id }

    override suspend fun update(tvShow: TvShow) {
        tvShows = tvShows.filterNot { it.id == tvShow.id } + tvShow
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var movies = defaultFakeMovies
    var tvShows = defaultFakeTvShows

    override suspend fun getPopularMovies(apiKey: String, region: String) = movies
    override suspend fun getPopularTvShows(apiKey: String): List<TvShow> = tvShows
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}