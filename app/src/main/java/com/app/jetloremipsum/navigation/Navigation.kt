package com.app.jetloremipsum.navigation

import androidx.annotation.StringRes
import com.app.jetloremipsum.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: Int?) {

    /** Login Screen */
    object Welcome: Screen("Welcome", R.string.welcome, null)

    /** Feed Screen */
    object Feed : Screen("Feed", R.string.feed, R.drawable.ic_round_home_24)
    object FeedDetails : Screen("FeedDetails", R.string.feed_details, null)

    /** Shop Screen */
    object Favorites: Screen("Home", R.string.favorites, R.drawable.ic_round_storefront_24)

    /** Notifications Screen */
    object Notification: Screen("Notifications", R.string.notifications, R.drawable.ic_round_notifications_24)

    /** Settings Screen */
    object Settings : Screen("Settings", R.string.settings, R.drawable.ic_round_menu_24)





}
