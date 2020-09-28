package com.orxeira.tvapp.ui.movies.moviemain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.domain.Movie
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ViewMovieBinding
import com.orxeira.tvapp.ui.common.basicDiffUtil
import com.orxeira.tvapp.ui.common.bindingInflate

class MovieAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_movie, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(val dataBinding: ViewMovieBinding) : RecyclerView.ViewHolder(dataBinding.root)
}