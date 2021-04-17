package com.app.jetloremipsum.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.jetloremipsum.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {
    object Feed : Screen("Feed", R.string.feed, Icons.Filled.Dashboard)
    object Settings : Screen("Settings", R.string.settings, Icons.Filled.Settings)
    object FeedDetails : Screen("FeedDetails", R.string.feed_details, null)
}
