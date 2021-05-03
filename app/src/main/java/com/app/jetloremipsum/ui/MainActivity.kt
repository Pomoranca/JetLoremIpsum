package com.app.jetloremipsum.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.app.jetloremipsum.navigation.Screen
import com.app.jetloremipsum.ui.feed.*
import com.app.jetloremipsum.ui.settings.SettingsScreen
import com.app.jetloremipsum.ui.welcome.ErrorSnackbar
import com.app.jetloremipsum.ui.welcome.SignIn
import com.app.jetloremipsum.ui.welcome.SignInEvent
import com.app.jetloremipsum.theme.OrderFoodAppTheme
import com.app.jetloremipsum.utils.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager


    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                AppContent()
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
        OrderFoodAppTheme(isNetworkAvailable = connectivityManager.isNetworkAvailable.value) {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                topBar = {
                    if (currentRoute(navController = navController) != Screen.Welcome.route) {
                        Column() {
                            TopBar(navController, snackbarHostState)
//
                        }
                    }
                },
                bottomBar =    {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ErrorSnackbar(
                            snackbarHostState = snackbarHostState,
                            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
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
                            viewModel = feedDetailsViewModel,
                        )
                    }

                    composable(Screen.Settings.route) { SettingsScreen(navController) }
                    composable(Screen.Favorites.route) {}
                    composable(Screen.Notification.route) {}


                }
            }
        }

    }


}











