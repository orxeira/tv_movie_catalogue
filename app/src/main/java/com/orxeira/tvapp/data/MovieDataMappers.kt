package com.orxeira.tvapp.data

import com.orxeira.domain.Movie
import com.orxeira.tvapp.data.database.model.Movie as DomainMovie
import com.orxeira.tvapp.data.server.model.Movie as ServerMovie


fun Movie.toRoomMovie(): DomainMovie =
    DomainMovie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

fun DomainMovie.toDomainMovie(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun ServerMovie.toDomainMovie(): Movie =
    Movie(
        0,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185$posterPath",
        if (backdropPath.isNullOrEmpty()
                .not()
        ) "https://image.tmdb.org/t/p/w780$backdropPath" else posterPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )