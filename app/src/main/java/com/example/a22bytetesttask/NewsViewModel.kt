package com.example.a22bytetesttask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a22bytetesttask.common.Resource
import com.example.a22bytetesttask.data.News
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewsViewModel: ViewModel() {

    private val _newsItems = MutableLiveData<Resource<List<News>>>()
    val newsItems: LiveData<Resource<List<News>>> = _newsItems

    init {
        getNews()
    }

    private fun getNews() {
        _newsItems.value = Resource.Loading()
        viewModelScope.launch {
            _newsItems.value = getNewsApi()
        }
    }

    private suspend fun getNewsApi(): Resource<List<News>> {
        return try {
            val news = NewsApiImpl.getListOfNews()
            Resource.Success(news)
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occured")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

}