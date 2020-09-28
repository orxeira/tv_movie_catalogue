package com.orxeira.tvapp

import android.app.Application
import com.orxeira.data.repository.MoviesRepository
import com.orxeira.data.repository.PermissionChecker
import com.orxeira.data.repository.RegionRepository
import com.orxeira.data.repository.TvShowRepository
import com.orxeira.data.source.LocationDataSource
import com.orxeira.data.source.MovieLocalDataSource
import com.orxeira.data.source.RemoteDataSource
import com.orxeira.data.source.TvShowLocalDataSource
import com.orxeira.tvapp.data.AndroidPermissionChecker
import com.orxeira.tvapp.data.PlayServicesLocationDataSource
import com.orxeira.tvapp.data.database.MovieDatabase
import com.orxeira.tvapp.data.database.MovieRoomDataSource
import com.orxeira.tvapp.data.database.TvShowRoomDataSource
import com.orxeira.tvapp.data.server.TheMovieDb
import com.orxeira.tvapp.data.server.TheMovieDbDataSource
import com.orxeira.tvapp.ui.movies.moviedetail.MovieDetailActivity
import com.orxeira.tvapp.ui.movies.moviedetail.MovieDetailViewModel
import com.orxeira.tvapp.ui.movies.moviemain.MovieMainActivity
import com.orxeira.tvapp.ui.movies.moviemain.MovieMainViewModel
import com.orxeira.tvapp.ui.tv.tvmain.TvMainActivity
import com.orxeira.tvapp.ui.tv.tvmain.TvMainViewModel
import com.orxeira.usecases.FindMovieById
import com.orxeira.usecases.GetPopularMovies
import com.orxeira.usecases.GetPopularTvShows
import com.orxeira.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        koin.loadModules(listOf(appModule, dataModule, scopesModule))
        koin.createRootScope()
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<MovieLocalDataSource> { MovieRoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource(get()) }
    factory<TvShowLocalDataSource> { TvShowRoomDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { TheMovieDb(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
    factory { TvShowRepository(get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MovieMainActivity>()) {
        viewModel { MovieMainViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<MovieDetailActivity>()) {
        viewModel { (id: Int) -> MovieDetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }

    scope(named<TvMainActivity>()) {
        viewModel { TvMainViewModel(get(), get()) }
        scoped { GetPopularTvShows(get()) }
    }
}