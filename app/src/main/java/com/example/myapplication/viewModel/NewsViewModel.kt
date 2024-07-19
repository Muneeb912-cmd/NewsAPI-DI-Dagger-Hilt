package com.example.myapplication.com.example.myapplication.viewModel

import android.net.http.HttpException
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repositories.Article
import com.example.myapplication.repositories.NewsRepository
import com.example.myapplication.repositories.NewsResponse
import com.example.myapplication.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<Resource<NewsResponse>>()
    val news: LiveData<Resource<NewsResponse>> = _news
    lateinit var articles: List<Article>
    var recyclerViewState: Parcelable? = null

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun fetchNews(query: String, apiKey: String) {
        viewModelScope.launch {
            _news.value = Resource.Loading()
            try {
                val response = repository.getNews(query, apiKey)
                if (response.isSuccessful) {
                    _news.value = Resource.Success(response.body())
                } else {
                    _news.value = Resource.Error(response.message())
                }
            } catch (e: IOException) {
                _news.value = Resource.Error("Network Failure: ${e.localizedMessage}")
            } catch (e: HttpException) {
                _news.value = Resource.Error("Server Error: ${e.localizedMessage}")
            } catch (e: Exception) {
                _news.value = Resource.Error("Unknown Error: ${e.localizedMessage}")
            }
        }
    }
}
