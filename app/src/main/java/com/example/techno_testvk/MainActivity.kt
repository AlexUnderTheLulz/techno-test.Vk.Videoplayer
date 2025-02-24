package com.example.techno_testvk

import com.example.techno_testvk.presentation.screen.VideoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.techno_testvk.presentation.screen.VideoPlayer
import com.example.techno_testvk.presentation.viewModel.VideoListViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val videosViewModel = ViewModelProvider(this)[VideoListViewModel::class.java]
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "video_screen"
            ) {
                composable("video_screen") {
                    VideoScreen(
                        navController = navController,
                        viewModel = videosViewModel
                    )
                }
                composable(
                    route = "video_item_screen/{videoUrl}",
                    arguments = listOf(navArgument("videoUrl") { type = NavType.StringType })
                ) { backStackEntry ->
                    val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
                    VideoPlayer(videoUrl = videoUrl)
                }
            }
        }
    }
}

