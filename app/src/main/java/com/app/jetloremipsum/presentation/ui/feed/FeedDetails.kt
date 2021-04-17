package com.app.jetloremipsum.presentation.ui.feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.app.jetloremipsum.result.Photo
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FeedDetailsScreen(
    feedId: Int?,
    viewModel: FeedDetailsViewModel
) {
val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
     //TODO get ITEM
    }


}