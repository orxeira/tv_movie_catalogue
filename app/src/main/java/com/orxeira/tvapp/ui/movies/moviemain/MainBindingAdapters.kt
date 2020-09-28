package com.orxeira.tvapp.ui.movies.moviemain

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.domain.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    (adapter as? MovieAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}