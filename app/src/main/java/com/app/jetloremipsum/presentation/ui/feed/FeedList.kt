package com.app.jetloremipsum.presentation.ui.feed

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.jetloremipsum.presentation.ui.Screen

//@Composable
//fun FeedList(
//    loading: Boolean,
//    feedItems: List<ResultItem>,
//    onChangeScrollPosition: (Int) -> Unit,
//    page: Int,
//    onTriggerNextPAge: () -> Unit,
//    onNavigateToFeedDetailsScreen: (String) -> Unit
//) {
//    Box(modifier = Modifier
//        .background(color = MaterialTheme.colors.surface)
//    ) {
//
//        if(loading && feedItems.isEmpty()) {
//            NothingHere()
//        } else {
//            LazyColumn {
//                itemsIndexed(
//                    items = feedItems
//                ) { index, item ->
//                    onChangeScrollPosition(index)
//                    if((index +1) >= (page * PAGE_SIZE) && !loading) {
//                        onTriggerNextPAge()
//                        }
//                    FeedItem(item = item, onItemClicked = {
//                        val route = Screen.FeedDetails.route + "/${it.id}"
//                        onNavigateToFeedDetailsScreen(route)
//                    })
//
//
//                }
//            }
//        }
//
//    }
//}