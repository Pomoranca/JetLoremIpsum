package com.app.jetloremipsum.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.app.jetloremipsum.Settings
import com.app.jetloremipsum.presentation.ui.Screen
import com.app.jetloremipsum.presentation.ui.feed.FeedDetailsScreen
import com.app.jetloremipsum.presentation.ui.feed.FeedDetailsViewModel
import com.app.jetloremipsum.presentation.ui.feed.FeedScreen
import com.app.jetloremipsum.presentation.ui.feed.FeedViewModel
import com.app.jetloremipsum.theme.OrderFoodAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OrderFoodAppTheme() {
                AppContent()
            }
        }
    }

    @Composable
    fun AppContent(
    ) {
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
                                    imageVector = screen.icon!!,
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

                composable(Screen.Feed.route) { navBackStackEntry ->
//                            val factory =
//                                HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//                            val feedViewModel: FeedViewModel = viewModel("FeedDetailsViewModel", factory)

                    val feedViewModel by viewModels<FeedViewModel>()
//
                    FeedScreen(
                        navigateTo = navController::navigate,
                        viewModel = feedViewModel,
                        loading = feedViewModel.loading.value
                    )
                }
                composable(Screen.Settings.route) { Settings(navController) }
                composable(
                    Screen.FeedDetails.route + "/{itemId}",
                    arguments = listOf(navArgument("itemId") {
                        type = NavType.IntType

                    })
                ) { navBackStackEntry ->
                    val feedDetailsViewModel by viewModels<FeedDetailsViewModel>()

                    FeedDetailsScreen(
                        feedId = navBackStackEntry.arguments?.getInt("itemId"),
                        viewModel = feedDetailsViewModel

                    )
                }
            }
        }
    }


}


val bottomItems = listOf(
    Screen.Feed,
    Screen.Settings
)









