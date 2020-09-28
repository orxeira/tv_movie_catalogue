package com.orxeira.tvapp.ui.movies.moviedetail

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.orxeira.domain.Movie
import com.orxeira.tvapp.R

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) = movie?.run {
    this@updateMovieDetails.setMovie(this)
}