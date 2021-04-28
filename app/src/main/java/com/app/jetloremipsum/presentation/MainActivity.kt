package com.app.jetloremipsum.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.app.jetloremipsum.presentation.ui.settings.Settings
import com.app.jetloremipsum.presentation.ui.Screen
import com.app.jetloremipsum.presentation.ui.feed.*
import com.app.jetloremipsum.presentation.ui.welcome.SignIn
import com.app.jetloremipsum.presentation.ui.welcome.SignInEvent
import com.app.jetloremipsum.theme.OrderFoodAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OrderFoodAppTheme() {
                AppContent()
            }
        }
    }

    @Composable
    fun currentRoute(navController: NavHostController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    }

    @Composable
    fun AppContent(
    ) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                if (currentRoute(navController = navController) != Screen.Welcome.route) {
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

//                                    // Avoid multiple copies of the same destination when
//                                    // reselecting the same item
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }

        )

        {

            NavHost(navController, startDestination = Screen.Feed.route) {

                composable(Screen.Welcome.route) { navBackStackEntry ->
                    val viewModel: SignInViewModel by viewModels { SignInViewModelFactory() }

                    viewModel.navigateTo.observe(this@MainActivity) { navigateToEvent ->
                        navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->

                            //prevent user from getting back to login screen once logged in
                            navController.popBackStack()
                            navController.navigate(navigateTo.route)

                        }
                    }

                    SignIn(onNavigationEvent = { event ->
                        when (event) {
                            is SignInEvent.SignIn -> {
                                viewModel.signIn(event.email, event.password)
                            }
                            SignInEvent.SignUp -> {
                                viewModel.signUp()
                            }
                            SignInEvent.SignInAsGuest -> {
                                viewModel.signInAsGuest()
                            }
                            SignInEvent.NavigateBack -> {

                            }
                        }
                    })


                }

                composable(Screen.Feed.route) { navBackStackEntry ->
                    val feedViewModel by viewModels<FeedViewModel>()

                    FeedScreen(
                        navigateTo = navController::navigate,
                        viewModel = feedViewModel,
                        loading = feedViewModel.loading.value,
                    )
                }

                composable(
                    Screen.FeedDetails.route + "/{itemId}",
                    arguments = listOf(navArgument("itemId") {
                        type = NavType.IntType

                    })
                ) { navBackStackEntry ->
                    val feedDetailsViewModel by viewModels<FeedDetailsViewModel>()

                    FeedDetailsScreen(
                        feedId = navBackStackEntry.arguments?.getInt("itemId"),
                        activity = this@MainActivity,
                        loading = feedDetailsViewModel.loading.value
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


}











