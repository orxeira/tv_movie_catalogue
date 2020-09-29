package com.orxeira.tvapp.ui.movies.moviedetail

import androidx.databinding.BindingAdapter
import com.orxeira.domain.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) = movie?.run {
    this@updateMovieDetails.setMovie(this)
}