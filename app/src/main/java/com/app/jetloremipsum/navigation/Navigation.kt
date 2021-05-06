package com.app.jetloremipsum.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.jetloremipsum.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {

    /** Login Screen */
    object Welcome: Screen("Welcome", R.string.welcome, null)

    /** Feed Screen */
    object Feed : Screen("Feed", R.string.feed, Icons.Filled.Home)
    object FeedDetails : Screen("FeedDetails", R.string.feed_details, null)

    /** Shop Screen */
    object Favorites: Screen("Home", R.string.favorites, Icons.Filled.Favorite)

    /** Notifications Screen */
    object Notification: Screen("Notifications", R.string.notifications, Icons.Filled.Notifications)

    /** Settings Screen */
    object Settings : Screen("Settings", R.string.settings, Icons.Filled.Settings)





}
