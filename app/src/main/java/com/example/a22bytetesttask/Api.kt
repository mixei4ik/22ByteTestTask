package com.example.a22bytetesttask

import com.example.a22bytetesttask.common.Constants.BASE_URL
import com.example.a22bytetesttask.data.ApiData
import com.example.a22bytetesttask.data.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsApi {
    @GET("/v2/everything?q=Apple&from=2022-04-14&sortBy=popularity&apiKey=9cdb1537a38b48cc8f7074a56c66d434")
    suspend fun getListOfNews(): ApiData
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