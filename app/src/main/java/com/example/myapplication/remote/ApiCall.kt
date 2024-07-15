package com.example.myapplication.remote

import com.example.myapplication.repositories.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(@Query("q") query: String, @Query("apiKey") apiKey: String): Response<NewsResponse>
}
