package com.orxeira.tvapp.ui.tv.tvdetail

import androidx.databinding.BindingAdapter
import com.orxeira.domain.TvShow

@BindingAdapter("tvShow")
fun TvShowDetailInfoView.updateTvShowDetails(tvShow: TvShow?) = tvShow?.run {
    this@updateTvShowDetails.setTvShow(this)
}