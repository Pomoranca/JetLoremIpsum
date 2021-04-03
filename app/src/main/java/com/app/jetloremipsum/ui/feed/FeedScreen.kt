package com.app.jetloremipsum.ui.feed

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.app.jetloremipsum.network.Status
import com.app.jetloremipsum.result.ResultItem
import com.app.jetloremipsum.theme.OrderFoodAppTheme

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    navController: NavHostController,
    items: List<ResultItem>,
) {
    viewModel.getResults().observe(lifecycleOwner, {
        it?.let { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { result ->
                        viewModel.addItems(result)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {
                    viewModel.startLoading()
                }
            }
        }
    })
    LazyColumn() {
        items(items = items) {
            FeedItem(item = it)
        }
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
fun FeedItem(
    item: ResultItem,
    modifier: Modifier = Modifier
) {
    Card(shape = RoundedCornerShape(10.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = item.title!!, style = MaterialTheme.typography.h4)
            Text(text = item.body!!)
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
