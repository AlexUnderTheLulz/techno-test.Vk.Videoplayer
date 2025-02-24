package com.example.techno_testvk.data

import com.example.techno_testvk.domain.VideoApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface VideoAPI {

    @GET("data.json")
    suspend fun getVideos(

    ): Response<VideoApiResponse>

}