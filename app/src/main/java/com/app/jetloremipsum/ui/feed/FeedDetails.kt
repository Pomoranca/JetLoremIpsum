package com.app.jetloremipsum.ui.feed

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.ui.welcome.ErrorSnackbar
import dev.chrisbanes.accompanist.coil.CoilImage
import com.app.jetloremipsum.utils.*
import kotlinx.coroutines.delay


@Composable
fun FeedDetailsScreen(
    feedId: Int?,
    viewModel: FeedDetailsViewModel,
) {

    val uiState = loadNetworkImage(id = feedId!!, repository = viewModel.repository)

    when (uiState.value) {
        is Result.Error -> {

        }
        is Result.Success -> {
            FeedDetails(photo = (uiState.value as Result.Success<Photo>).data)
        }
        is Result.Loading -> {
            FullScreenLoading(true)
        }
    }
}

@Composable
fun loadNetworkImage(
    id: Int,
    repository: PostsRepository,
): State<Result<Photo>> {

    // Creates a State<T> with Result.Loading as initial value
    // If either `url` or `imageRepository` changes, the running producer
    // will cancel and will be re-launched with the new keys.
    return produceState(initialValue = Result.Loading, id, repository) {

        //because API is fast, simulate 300ms delay
        delay(300)

        // In a coroutine, can make suspend calls
        val image = repository.getPhoto(id)

        // Update State with either an Error or Success result.
        // This will trigger a recomposition where this State is read
        value = if(image.albumId == -1){
            Result.Error("ERROR")

        } else {
            Result.Success(image)

        }

    }
}


@Composable
fun FeedDetails(photo: Photo, modifier: Modifier = Modifier) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoilImage(
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











