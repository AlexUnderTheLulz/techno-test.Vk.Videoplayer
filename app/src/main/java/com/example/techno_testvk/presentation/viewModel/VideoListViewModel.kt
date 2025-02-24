package com.example.techno_testvk.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techno_testvk.data.NetworkResponse
import com.example.techno_testvk.data.RetrofitInstance
import com.example.techno_testvk.domain.VideoApiResponse
import kotlinx.coroutines.launch

class VideoListViewModel: ViewModel() {

    private val videosApi = RetrofitInstance.videoApi
    private val _videosResult = MutableLiveData<NetworkResponse<VideoApiResponse>>()
    val videosResult: LiveData<NetworkResponse<VideoApiResponse>> = _videosResult

    fun getVideos() {

        viewModelScope.launch {
            try {
                val responce = videosApi.getVideos()
                if (responce.isSuccessful){
                    responce.body()?.let {
                        _videosResult.value = NetworkResponse.Success(it)
                    }
                }
            }
            catch (e: Exception) {
                _videosResult.value = NetworkResponse.Error("Неизвестная ошибка")
            }
        }
    }

    init{
        getVideos()
    }
}