package com.orxeira.tvapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.orxeira.tvapp.R
import com.orxeira.tvapp.data.server.TheMovieDb
import com.orxeira.tvapp.ui.tv.tvmain.TvShowMainActivity
import com.orxeira.tvapp.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class TvShowsUITest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(TvShowMainActivity::class.java, false, false)

//    @get:Rule
//    val grantPermissionRule: GrantPermissionRule =
//        GrantPermissionRule.grant(
//            "android.permission.ACCESS_COARSE_LOCATION"
//        )

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("populartvshows.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<TheMovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.rvTvShowList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.tvShowDetailToolbar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Marvel's Daredevil"))))
    }
}