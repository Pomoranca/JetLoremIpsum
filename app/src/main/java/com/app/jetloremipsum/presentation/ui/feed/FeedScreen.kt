package com.app.jetloremipsum.presentation.ui.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.app.jetloremipsum.R
import com.app.jetloremipsum.presentation.ui.navigation.Screen
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.theme.*
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.launch


@Composable
fun FeedScreen(
    loading: Boolean,
    viewModel: FeedViewModel,
    navigateTo: (String) -> Unit,
    ) {
    val feed = viewModel.photos.value

    Scaffold(
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            PostList(
                loading = loading,
                posts = feed,
                navigateTo = navigateTo
            )
        })
}

@Composable
fun TopBar(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val scope = rememberCoroutineScope()
    val snackbarErrorText = stringResource(id = R.string.feature_not_available)
    val snackbarActionLabel = stringResource(id = R.string.dismiss)

    TopAppBar(
        backgroundColor = Color.White,
        title = {

            Icon(
                painter = painterResource(id = R.drawable.jetloremipsum_title),
                contentDescription = null,
                modifier = Modifier.width(200.dp),
                tint = Purple500
            )
        },
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = snackbarErrorText,
                            actionLabel = snackbarActionLabel
                        )
                    }
                },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconsBackground)

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_search_24),
                    contentDescription = "",
                    tint = Color.Black,
                )
            }
            Spacer(modifier = Modifier.padding(start = 16.dp))
        },
    )
    TabsLayout(navController = navController)
}

val bottomItems = listOf(
    Screen.Feed,
    Screen.Favorites,
    Screen.Notification,
    Screen.Settings,
    )

@Composable
fun TabsLayout(navController: NavHostController) {

    val selectedTabIndex = remember { mutableStateOf(0) }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabRowDefaults.Indicator(
            color = colorResource(R.color.purple_500),
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value])
        )
    }

    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        indicator = indicator,
        backgroundColor = Color.White
    ) {

        bottomItems.forEachIndexed { index, screen ->
            Tab(
                selected = false,
                onClick = {
                    selectedTabIndex.value = index
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
//                                        // avoid building up a large stack of destinations
//                                        // on the back stack as users select items
                        popUpTo = navController.graph.startDestination
//
////                                    // Avoid multiple copies of the same destination when
////                                    // reselecting the same item
                        launchSingleTop = true
                    }
                },

            ) {
                Icon(
                    painter = painterResource(screen.icon!!),
                    contentDescription = stringResource(id = screen.resourceId),
                    modifier = Modifier.padding(16.dp),
                    tint = if (selectedTabIndex.value == index) Purple500 else Color.Gray,
                )
            }
        }
    }

}

@Composable
fun PostList(
    loading: Boolean,
    posts: List<Photo>,
    navigateTo: (String) -> Unit,
) {
    if (loading && posts.isEmpty()) {
        FullScreenLoading()

    } else {
        LazyColumn {
            itemsIndexed(
                items = posts
            ) { index, item ->
                FeedItem(item = item, onClick = {
                    val route = Screen.FeedDetails.route + "/${item.id}"
                    navigateTo(route)
                })
            }
        }
    }

}

@Composable
fun PostTitle(post: Photo) {
    Text(post.title, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun FeedItem(
    item: Photo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(Modifier.padding(16.dp)) {
            CoilImage(
                data = item.url,
                contentDescription = item.title,
                modifier = modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            PostTitle(post = item)
        }
    }
}


@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}
