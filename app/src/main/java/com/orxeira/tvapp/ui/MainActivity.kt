package com.orxeira.tvapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityMainBinding
import com.orxeira.tvapp.ui.MainViewModel.Navigation.Movies
import com.orxeira.tvapp.ui.MainViewModel.Navigation.TvShows
import com.orxeira.tvapp.ui.common.EventObserver
import com.orxeira.tvapp.ui.common.startActivity
import com.orxeira.tvapp.ui.movies.moviemain.MovieMainActivity
import com.orxeira.tvapp.ui.tv.tvmain.TvShowMainActivity
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.mViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigation.observe(this, EventObserver { navigation ->
            when (navigation) {
                Movies -> startActivity<MovieMainActivity> { }
                TvShows -> startActivity<TvShowMainActivity> { }
            }
        })
    }
}