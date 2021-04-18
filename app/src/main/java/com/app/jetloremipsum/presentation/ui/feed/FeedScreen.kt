package com.app.jetloremipsum.presentation.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.jetloremipsum.R
import com.app.jetloremipsum.presentation.components.HorizontalDottedProgressBar
import com.app.jetloremipsum.presentation.ui.Screen
import com.app.jetloremipsum.result.Photo
import dev.chrisbanes.accompanist.coil.CoilImage


@Composable
fun FeedScreen(
    loading: Boolean,
    viewModel: FeedViewModel,
    navigateTo: (String) -> Unit

) {
    val feed = viewModel.photos.value
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) }

            )
        },
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
fun ListItemDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}


@Composable
fun PostList(
    loading: Boolean,
    posts: List<Photo>,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (loading && posts.isEmpty()) {
        FullScreenLoading()

    } else {
        LazyColumn {
            itemsIndexed(
                items = posts) { index, item ->
                FeedItem(item = item, onClick = {
                    val route = Screen.FeedDetails.route + "/${item.id}"
                    navigateTo(route)
                })
            }
        }
    }

}

//@Composable
//fun PhotoListSelection(
//    posts: List<Photo>,
//    onClick: () -> Unit
//) {
//    Column {
//        posts.forEach { post ->
//            FeedItem(
//                item = post,
//                onClick = onClick
//            )
//            PostListDivider()
//        }
//    }
//}


@Composable
fun PostTitle(post: Photo) {
    Text(post.title, style = MaterialTheme.typography.subtitle1)
}

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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
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
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}
