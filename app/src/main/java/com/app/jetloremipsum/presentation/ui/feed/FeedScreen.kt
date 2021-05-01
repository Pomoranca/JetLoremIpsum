package com.app.jetloremipsum.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.app.jetloremipsum.R
import com.app.jetloremipsum.presentation.ui.navigation.Screen
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.theme.Purple500
import com.app.jetloremipsum.theme.darkFontColor
import com.app.jetloremipsum.theme.iconsBackground
import com.app.jetloremipsum.theme.lightFontColor
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
                navigateTo = navigateTo,
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
fun PostTitle(post: Photo, modifier: Modifier = Modifier) {
    Column(Modifier.padding(bottom = 8.dp)) {
        Text(
            text = "Photo ${post.id} ",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            color = darkFontColor
        )
        Text(
            modifier = modifier,
            text = post.title,
            style = MaterialTheme.typography.subtitle1,
            color = lightFontColor,
            fontStyle = FontStyle.Italic
        )
    }


}


@Composable
fun FeedItem(
    item: Photo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            .defaultMinSize(minHeight = 120.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 6.dp
    ) {
        Row(Modifier.padding(start = 8.dp, end = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                CoilImage(
                    data = item.url,
                    fadeIn = true,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(90.dp)
                )

            Column(modifier = modifier.padding(start = 16.dp)) {
                PostTitle(post = item)
            }

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
        val animationSpec = remember { LottieAnimationSpec.Asset("loading.json") }

        // You can control isPlaying/progress/repeat/etc. with this.
        val animationState =
            rememberLottieAnimationState(autoPlay = true, initialProgress = 0.45f)

        LottieAnimation(
            spec = animationSpec,
            modifier = Modifier.size(200.dp),
            animationState = animationState,

            )
    }
}






