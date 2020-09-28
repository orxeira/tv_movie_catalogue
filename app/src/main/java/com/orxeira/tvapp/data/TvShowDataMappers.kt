package com.orxeira.tvapp.data

import com.orxeira.domain.TvShow
import com.orxeira.tvapp.data.database.model.TvShow as RoomTvShow
import com.orxeira.tvapp.data.server.model.TvShow as ServerTvShow

fun TvShow.toRoomTvShow(): RoomTvShow =
    RoomTvShow(
        id,
        name,
        overview,
        firstAirDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalName,
        popularity,
        voteAverage,
        favorite
    )

fun RoomTvShow.toDomainTvShow(): TvShow = TvShow(
    id,
    name,
    overview,
    firstAirDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalName,
    popularity,
    voteAverage,
    favorite
)

fun ServerTvShow.toDomainTvShow(): TvShow =
    TvShow(
        0,
        name,
        overview,
        firstAirDate,
        "https://image.tmdb.org/t/p/w185$posterPath",
        if (backdropPath.isNullOrEmpty()
                .not()
        ) "https://image.tmdb.org/t/p/w780$backdropPath" else posterPath,
        originalLanguage,
        originalName,
        popularity,
        voteAverage,
        false
    )