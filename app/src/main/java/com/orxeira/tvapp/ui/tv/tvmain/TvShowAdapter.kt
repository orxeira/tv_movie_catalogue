package com.orxeira.tvapp.ui.tv.tvmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.domain.TvShow
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ViewTvshowBinding
import com.orxeira.tvapp.ui.common.basicDiffUtil
import com.orxeira.tvapp.ui.common.bindingInflate

class TvShowAdapter(private val listener: (TvShow) -> Unit) :
    RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    var tvShows: List<TvShow> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_tvshow, false))

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = tvShows[position]
        holder.dataBinding.tvShow = tvShow
        holder.itemView.setOnClickListener { listener(tvShow) }
    }

    class ViewHolder(val dataBinding: ViewTvshowBinding) : RecyclerView.ViewHolder(dataBinding.root)
}