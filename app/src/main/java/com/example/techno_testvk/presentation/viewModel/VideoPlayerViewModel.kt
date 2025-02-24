package com.example.techno_testvk.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayerViewModel(application: Application) : AndroidViewModel(application) {
    private var exoPlayer: ExoPlayer? = null

    private val context get() = getApplication<Application>().applicationContext

    fun initializePlayer(videoUrl: String): ExoPlayer {
        return ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            exoPlayer = this
        }
    }

    fun releasePlayer() {
        exoPlayer?.let {
            it.release()
            exoPlayer = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}