package com.app.jetloremipsum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.app.jetloremipsum.network.ApiHelper
import com.app.jetloremipsum.network.RetrofitBuilder
import com.app.jetloremipsum.theme.OrderFoodAppTheme
import com.app.jetloremipsum.ui.NavigationHost
import com.app.jetloremipsum.ui.feed.FeedViewModel
import com.app.jetloremipsum.ui.feed.FeedViewModelFactory
import com.app.jetloremipsum.ui.feed.addItems

class MainActivity : ComponentActivity() {

    private val feedViewModel: FeedViewModel by viewModels {
        FeedViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderFoodAppTheme() {
                OrderFoodApp()
            }
        }
    }

    @Composable
    fun OrderFoodApp() {
        addItems(
            viewModel = feedViewModel,
            lifecycleOwner = this,
            context = this
        )
        NavigationHost(feedViewModel)
    }
}








