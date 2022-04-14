package com.example.a22bytetesttask

import com.example.a22bytetesttask.common.Constants.API_KEY
import com.example.a22bytetesttask.common.Constants.BASE_URL
import com.example.a22bytetesttask.data.ApiData
import com.example.a22bytetesttask.data.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/everything?q=bitcoin")
    suspend fun getListOfNews(
        @Query("apiKey") key: String = API_KEY
    ): ApiData
}

object NewsApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val newsApiService = retrofit.create(NewsApi::class.java)

    suspend fun getListOfNews(): List<News> {
        return withContext(Dispatchers.IO) {
            var id = 0
            newsApiService.getListOfNews()
                .articles
                .map { article ->
                    News(
                        id = id++,
                        author = article.author ?: "not author",
                        content = article.content ?: "not content",
                        description = article.description ?: "not description",
                        publishedAt = article.publishedAt ?: "unknown",
                        title = article.title ?: "Title",
                        url = article.url ?: "not url",
                        urlToImage = article.urlToImage ?: ""
                    )
                }
        }
    }
}