package com.example.myapplication.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.remote.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    private val newsDataByTitle = MutableLiveData<Response<NewsResponse>>()

    suspend fun getNews(query: String, apiKey: String): Response<NewsResponse> {
        return newsApi.getNews(query, apiKey)
    }

}