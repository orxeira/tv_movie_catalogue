package com.orxeira.tvapp.ui.tv.tvdetail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.orxeira.domain.TvShow
import java.util.*

class TvShowDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setTvShow(tvShow: TvShow) = with(tvShow) {
        text = buildSpannedString {

            bold { append("Original language: ") }
            appendLine(Locale(originalLanguage).displayLanguage.capitalize(Locale.getDefault()))

            bold { append("Original name: ") }
            appendLine(originalName)

            bold { append("First air date: ") }
            appendLine(firstAirDate)

            bold { append("Popularity: ") }
            appendLine(popularity.toString())

            bold { append("Vote Average: ") }
            append(voteAverage.toString())
        }
    }
}