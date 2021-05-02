package com.app.jetloremipsum.ui.feed

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.ui.welcome.ErrorSnackbar
import com.app.jetloremipsum.utils.UIResponseState
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun FeedDetailsScreen(
    feedId: Int?,
    viewModel: FeedDetailsViewModel

) {

    //TODO data not showing on first load

    viewModel.getFeedItem(feedId!!)

    when (viewModel.viewState.value) {
        is UIResponseState.Loading -> {
            FullScreenLoading()
        }
        is UIResponseState.Error -> {

        }

        is UIResponseState.Success<*> -> {
            FeedDetails(photo = viewModel.photo.value!!)
        }
    }
}

@Composable
fun FeedDetails(photo: Photo, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoilImage(
            fadeIn = true,

            data = photo.url,
            contentDescription = photo.title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)

        ) {}


        Text(text = photo.title)


    }
}











