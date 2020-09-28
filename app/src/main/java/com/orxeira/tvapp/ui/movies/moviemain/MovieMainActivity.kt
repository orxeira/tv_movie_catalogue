package com.orxeira.tvapp.ui.movies.moviemain

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityMovieMainBinding
import com.orxeira.tvapp.ui.common.EventObserver
import com.orxeira.tvapp.ui.common.PermissionRequester
import com.orxeira.tvapp.ui.common.startActivity
import com.orxeira.tvapp.ui.movies.moviedetail.MovieDetailActivity
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MovieMainActivity : AppCompatActivity() {

    private lateinit var adapter: MovieAdapter
    private val coarsePermissionRequester =
        PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    private val viewModel: MovieMainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMovieMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_main)
        binding.mViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = MovieAdapter(viewModel::onMovieClicked)
        binding.rvMovieList.adapter = adapter

        viewModel.navigateToMovie.observe(this, EventObserver { id ->
            startActivity<MovieDetailActivity> {
                putExtra(MovieDetailActivity.MOVIE, id)
            }
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })
    }
}