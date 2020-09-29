package com.orxeira.tvapp.ui.tv.tvmain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityTvShowMainBinding
import com.orxeira.tvapp.ui.common.EventObserver
import com.orxeira.tvapp.ui.common.startActivity
import com.orxeira.tvapp.ui.tv.tvdetail.TvShowDetailActivity
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class TvShowMainActivity : AppCompatActivity() {

    private lateinit var adapter: TvShowAdapter

    private val viewModel: TvShowMainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityTvShowMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_tv_show_main)
        binding.mViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = TvShowAdapter(viewModel::onTvShowClicked)
        binding.rvTvShowList.adapter = adapter

        viewModel.navigateToTvShow.observe(this, EventObserver { id ->
            startActivity<TvShowDetailActivity> {
                putExtra(TvShowDetailActivity.TV_SHOW, id)
            }
        })
    }
}