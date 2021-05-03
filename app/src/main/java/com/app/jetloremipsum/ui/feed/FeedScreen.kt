package com.app.jetloremipsum.ui.feed

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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.paging.*
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.app.jetloremipsum.R
import com.app.jetloremipsum.navigation.Screen
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.theme.Purple500
import com.app.jetloremipsum.theme.darkFontColor
import com.app.jetloremipsum.theme.iconsBackground
import com.app.jetloremipsum.theme.lightFontColor
import com.app.jetloremipsum.utils.ErrorItem
import com.app.jetloremipsum.utils.LoadingItem
import com.app.jetloremipsum.utils.LoadingView
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    navigateTo: (String) -> Unit,
) {


    Scaffold(
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            PostList(
                posts = viewModel.photos,
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
//                      // avoid building up a large stack of destinations
//                      // on the back stack as users select items
                        popUpTo = navController.graph.startDestination
//
//                      // Avoid multiple copies of the same destination when
//                      // reselecting the same item
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
    posts: Flow<PagingData<Photo>>,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyPhotoItems = posts.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(lazyPhotoItems
        )
        { item ->
            FeedItem(item = item!!, onClick = {
                val route = Screen.FeedDetails.route + "/${item.id}"
                navigateTo(route)
            })
        }

        lazyPhotoItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { FullScreenLoading(fastLoad = false) }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyPhotoItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() })

                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyPhotoItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage!!,
                            onClickRetry = { retry() })


                    }
                }
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
    modifier: Modifier = Modifier,
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
        Row(
            Modifier.padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
fun FullScreenLoading(fastLoad: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        var initialProgress = 0.25f

        if (fastLoad) initialProgress = 0.55f

        val animationSpec = remember { LottieAnimationSpec.Asset("loading.json") }

        // You can control isPlaying/progress/repeat/etc. with this.
        val animationState =
            rememberLottieAnimationState(autoPlay = true, initialProgress = initialProgress)

        LottieAnimation(
            spec = animationSpec,
            modifier = Modifier.size(200.dp),
            animationState = animationState,

            )
    }
}






