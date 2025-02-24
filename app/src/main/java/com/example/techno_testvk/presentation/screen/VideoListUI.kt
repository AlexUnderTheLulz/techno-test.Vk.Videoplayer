package com.example.techno_testvk.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.techno_testvk.data.NetworkResponse
import com.example.techno_testvk.domain.Video
import com.example.techno_testvk.presentation.viewModel.VideoListViewModel
import java.net.URLEncoder


@Composable
fun VideoScreen(
    navController: NavController,
    viewModel: VideoListViewModel
) {

    val videosResult = viewModel.videosResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        IconButton(
            onClick = { viewModel.getVideos() }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                tint = Color.Black,
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (val result = videosResult.value) {
                is NetworkResponse.Success -> VideoList(
                    videos = result.data.categories.flatMap { it.videos },
                    onVideoClick = { url ->
                        navController.navigate("video_item_screen/$url")
                    }
                )

                is NetworkResponse.Error -> Text(text = result.message)
                is NetworkResponse.Loading -> Text(text = "Загрузка")
                null -> Text(text = "Обновите данные")
            }
        }
    }
}

@Composable
fun VideoList(
    videos: List<Video>,
    onVideoClick: (String) -> Unit
) {
    LazyColumn {
        items(videos) { video ->
            VideoItem(
                video = video,
                onVideoClick = onVideoClick
            )
        }
    }
}

@Composable
fun VideoItem(
    video: Video,
    onVideoClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                val rawUrl = video.sources.first()
                val encodedUrl = URLEncoder.encode(rawUrl, "UTF-8")
                onVideoClick(encodedUrl)
            }
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .height(220.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = video.thumb,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = video.title)
    }
}