package com.app.jetloremipsum.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState

@Composable
fun FavoritesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val animationSpec = remember { LottieAnimationSpec.Asset("error_404.json") }

        // You can control isPlaying/progress/repeat/etc. with this.
        val animationState =
            rememberLottieAnimationState(autoPlay = true)

        LottieAnimation(
            spec = animationSpec,
            modifier = Modifier.fillMaxWidth(),
            animationState = animationState,

            )
    }
}