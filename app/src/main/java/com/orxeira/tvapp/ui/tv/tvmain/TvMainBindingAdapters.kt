package com.orxeira.tvapp.ui.tv.tvmain

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.domain.TvShow

@BindingAdapter("items")
fun RecyclerView.setItems(tvShows: List<TvShow>?) {
    (adapter as? TvShowAdapter)?.let {
        it.tvShows = tvShows ?: emptyList()
    }
}