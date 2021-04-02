package com.app.jetloremipsum.ui

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import com.app.jetloremipsum.R
import com.app.jetloremipsum.Settings
import com.app.jetloremipsum.ui.feed.FeedScreen
import com.app.jetloremipsum.ui.feed.FeedViewModel

@Composable
fun NavigationHost(feedViewModel: FeedViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                bottomItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo = navController.graph.startDestination
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {

        NavHost(navController, startDestination = Screen.Feed.route) {

            composable(Screen.Feed.route) {
                FeedScreen(
                    navController,
                    feedViewModel.resultItems
                )
            }
            composable(Screen.Settings.route) { Settings(navController) }
        }
    }
}

val bottomItems = listOf(
    Screen.Feed,
    Screen.Settings
)

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Feed : Screen("Feed", R.string.feed, Icons.Filled.Dashboard)
    object Settings : Screen("Settings", R.string.settings, Icons.Filled.Settings)
}