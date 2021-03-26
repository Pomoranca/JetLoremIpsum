package com.app.orderfoodapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Profile(navController: NavHostController) {
    ListViewContent()

}

@Composable
fun ListViewContent() {
    Scaffold(content = {
        VerticalList()
    })
}

@Composable
fun VerticalList() {
    val list = remember { itemsList }
    LazyColumn {
        items(items = list,
            itemContent = { item ->
                FeedItem(item = item)
                ListItemDivider()
            })
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
fun FeedItem(item: Item, modifier: Modifier = Modifier) {
    Card() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CoilImage(data = item.imageresource) {}
            Spacer(Modifier.height(16.dp))
            Text(text = item.title)
        }
    }
}

val itemsList = listOf(
    Item(1, R.drawable.abc_vector_test, "Fresh veggies"),
    Item(2, R.drawable.abc_vector_test, "Vegetables"),
    Item(3, R.drawable.abc_vector_test, "Apples"),
    Item(4, R.drawable.abc_vector_test, "Cinamon"),
)

data class Item(
    val id: Int,
    val imageresource: Int,
    val title: String
)