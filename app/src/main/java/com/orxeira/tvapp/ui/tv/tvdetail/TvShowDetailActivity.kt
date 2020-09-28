package com.orxeira.tvapp.ui.tv.tvdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orxeira.tvapp.R

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val TV_SHOW = "TvShowDetailActivity:tvShow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
    }
}