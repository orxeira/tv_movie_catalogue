package com.orxeira.tvapp.ui.movies.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orxeira.tvapp.R
import com.orxeira.tvapp.databinding.ActivityMovieDetailBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "MovieDetailActivity:movie"
    }

    private val viewModel: MovieDetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(intent.getIntExtra(MOVIE, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        binding.mViewModel = viewModel
        binding.lifecycleOwner = this
    }
}