package com.orxeira.tvapp.ui.tv.tvdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityTvShowDetailBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val TV_SHOW = "TvShowDetailActivity:tvShow"
    }

    private val viewModel: TvShowDetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(intent.getIntExtra(TV_SHOW, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTvShowDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_tv_show_detail)

        binding.mViewModel = viewModel
        binding.lifecycleOwner = this

    }
}