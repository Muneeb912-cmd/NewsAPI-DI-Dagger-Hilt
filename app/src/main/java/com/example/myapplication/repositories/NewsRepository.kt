package com.example.myapplication.repositories

import com.example.myapplication.remote.NewsApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getNews(query: String, apiKey: String): Response<NewsResponse> {
        return newsApi.getNews(query, apiKey)
    }
}