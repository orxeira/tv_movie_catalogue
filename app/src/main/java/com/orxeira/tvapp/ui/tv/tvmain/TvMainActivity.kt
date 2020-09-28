package com.orxeira.tvapp.ui.tv.tvmain

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityTvMainBinding
import com.orxeira.tvapp.ui.common.EventObserver
import com.orxeira.tvapp.ui.common.PermissionRequester
import com.orxeira.tvapp.ui.common.startActivity
import com.orxeira.tvapp.ui.tv.tvdetail.TvShowDetailActivity
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class TvMainActivity : AppCompatActivity() {

    private lateinit var adapter: TvShowAdapter

    private val viewModel: TvMainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityTvMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_tv_main)
        binding.mViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = TvShowAdapter(viewModel::onTvShowClicked)
        binding.rvTvShowList.adapter

        viewModel.navigateToTvShow.observe(this, EventObserver { id ->
            startActivity<TvShowDetailActivity> {
                putExtra(TvShowDetailActivity.TV_SHOW, id)
            }
        })
    }
}