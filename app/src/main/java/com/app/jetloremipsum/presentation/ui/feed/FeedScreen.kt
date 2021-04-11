package com.app.jetloremipsum.presentation.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.jetloremipsum.R
import com.app.jetloremipsum.presentation.ui.Screen
import com.app.jetloremipsum.presentation.ui.SwipeToRefreshLayout
import com.app.jetloremipsum.presentation.ui.state.UiState
import com.app.jetloremipsum.repository.impl.PostsRepositoryImpl
import com.app.jetloremipsum.result.Photo
import com.example.jetnews.utils.produceUiState
import dev.chrisbanes.accompanist.coil.CoilImage


@Composable
fun FeedScreen(
    navigateTo: (Screen) -> Unit,
    viewModel: FeedViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {

    val (postUiState, refreshPost, clearError) = produceUiState(viewModel.repositoryImpl) {
     getPhotos()
    }

    FeedScreen(
        posts = postUiState.value,
        onRefreshPosts = refreshPost,
        onErrorDismiss = clearError,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )


}

@Composable
fun FeedScreen(
    posts: UiState<List<Photo>>,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState

) {
    if (posts.hasError) {
        val errorMessage = stringResource(id = R.string.load_error)
        val retryMessage = stringResource(id = R.string.retry)

        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onRefreshPostsState by rememberUpdatedState(onRefreshPosts)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        // Show snackbar using a coroutine, when the coroutine is cancelled the snackbar will
        // automatically dismiss. This coroutine will cancel whenever posts.hasError is false
        // (thanks to the surrounding if statement) or if scaffoldState changes.
        LaunchedEffect(scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = retryMessage
            )
            when (snackbarResult) {
                SnackbarResult.ActionPerformed -> onRefreshPostsState()
                SnackbarResult.Dismissed -> onErrorDismissState()
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) }

            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            LoadingContent(
                empty = posts.initialLoad,
                emptyContent = { FullScreenLoading() },
                loading = posts.loading,
                onRefresh = onRefreshPosts,
                content = {
                    FeedScreendErrorAndContent(
                        posts = posts,
                        onRefresh = onRefreshPosts,
                        navigateTo = navigateTo,
                        modifier = modifier
                    )
                }
            )
        }
    )

}


/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param loading (state) when true, display a loading spinner over [content]
 * @param onRefresh (event) event to request refresh
 * @param content (slot) the main content to show
 */
@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeToRefreshLayout(
            refreshingState = loading,
            onRefresh = onRefresh,
            refreshIndicator = {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                    )
                }
            },
            content = content,
        )
    }
}


@Composable
fun ListItemDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun FeedScreendErrorAndContent(
    posts: UiState<List<Photo>>,
    onRefresh: () -> Unit,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    if (posts.data != null) {
        PostList(posts.data, navigateTo, modifier)
    } else if (!posts.hasError) {
        // if there are no posts, and no error, let the user refresh manually
        TextButton(onClick = onRefresh, modifier.fillMaxSize()) {
            Text("Tap to load content", textAlign = TextAlign.Center)
        }
    } else {
        // there's currently an error showing, don't show any content
        Box(modifier.fillMaxSize()) { /* empty screen */ }
    }
}

@Composable
fun PostList(
    posts: List<Photo>,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val postsTop = posts

    LazyColumn(modifier = Modifier) {
        item { PhotoListSelection(postsTop, navigateTo) }
    }
}

@Composable
fun PhotoListSelection(
    posts: List<Photo>,
    navigateTo: (Screen) -> Unit
) {
    Column {
        posts.forEach { post ->
            PhotoCard(
                post = post,
                navigateTo = navigateTo
            )
            PostListDivider()
        }
    }
}

@Composable
fun PhotoCard(
    post: Photo,
    navigateTo: (Screen) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { navigateTo(Screen.FeedDetails(post.id.toString())) })
            .padding(16.dp)

    ) {
        CoilImage(data = post.thumbnailUrl) {}
        Column(modifier = Modifier.weight(1f)) {
            PostTitle(post = post)
        }


    }
}

@Composable
fun PostTitle(post: Photo) {
    Text(post.title, style = MaterialTheme.typography.subtitle1)
}


/**
 * Full-width divider with padding for [PostList]
 */
@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}


@Composable
fun FeedItem(
    item: Photo,
    onItemClicked: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .clickable { onItemClicked(item) }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CoilImage(
                data = item.thumbnailUrl,
                contentDescription = item.title,
                modifier = modifier
                    .fillMaxWidth()
                    .height(225.dp),
                contentScale = ContentScale.Crop
            ) {
            }
            Text(text = item.title, style = MaterialTheme.typography.h4)

            ListItemDivider()

        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}
