package com.example.techno_testvk.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import android.view.ViewGroup
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.remember
import com.example.techno_testvk.presentation.viewModel.VideoPlayerViewModel


@Composable
fun VideoPlayer(
    videoUrl: String,
    viewModel: VideoPlayerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    if (videoUrl.isBlank()) {
        Text("Invalid video URL")
        return
    }

    val exoPlayer = remember {
        viewModel.initializePlayer(videoUrl)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16f / 9f)
    )
}